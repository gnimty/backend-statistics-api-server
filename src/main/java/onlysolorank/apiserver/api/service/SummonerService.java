package onlysolorank.apiserver.api.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import onlysolorank.apiserver.api.controller.dto.HasPlayedRes;
import onlysolorank.apiserver.api.controller.dto.IngameInfoRes;
import onlysolorank.apiserver.api.controller.dto.SummonerMatchRes;

import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.domain.*;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.repository.summoner.SummonerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Pattern;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static onlysolorank.apiserver.utils.CustomFunctions.keywordToInternalName;


/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerService
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/24        solmin       internalName으로 SummonerDto 조회하는 기능 구현
 * 2023/07/28        solmin       keywordToInternalName 메소드를 KeywordRequestDto로 이관
 * 2023/07/31        solmin       getSummonerMatchInfoBySummonerName에 필요한 DTO 구현 거의 완성 (미완료)
 * 2023/08/09        solmin       getAllChampionPlayInfoByPuuid 서비스 메소드 구현 , 이전 코드 일부 활용
 * 2023/08/09        solmin       계층적으로 match, team, participant에 대하여 순차적인 layer로 단계 나누기
 * SummonerService
 * - SummonerMatchService
 * - ParticipantService
 * - MatchService
 * - ParticipantService
 * - TeamService
 * 2023/08/15        solmin       소환사 랭크 티어 그래프 조회 메소드 추가
 * 2023/08/15        solmin       일부 중복되는 코드 Inline
 * 2023/08/16        solmin       MMR 기준 소환사 랭킹 정보 조회 메소드 추가 구현
 * 2023/08/28        solmin       챔피언 장인랭킹 구현
 * 2023/08/30        solmin       소환사 인게임 정보 구현, restTemplate로 받아온 정보 convert 및 추가정보 붙여서 보내주기
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerService {


    private final SummonerRepository summonerRepository;
    private final SummonerMatchService summonerMatchService;
    private final MatchService matchService;
    private final ParticipantService participantService;
    private final SummonerPlayService summonerPlayService;
    private final SummonerHistoryService summonerHistoryService;

    @Value("${batch.host}")
    private String BATCH_HOST;

    @Value("${batch.port}")
    private String BATCH_PORT;

    @Value("${riot.api-key}")
    private String RIOT_API_KEY;

    private static final int SPECIALIST_CNT_LIMIT = 100;
    private static final int SPECIALIST_PLAYS_CNT_LIMIT = 50;


    public List<SummonerDto> getTop5SummonerDtoListByInternalName(String internalName) {

        return summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(SummonerDto::new)
            .toList();

    }

    public SummonerMatchRes getSummonerMatchInfoBySummonerName(String internalName) {

        Summoner summoner = getSummonerBySummonerName(internalName);

        // MatchId 및 MatchDto List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchService.getSummonerMatchBySummonerPuuid(summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                .limit(20).toList();
            matchDtoList = getMatchDtoList(matchIds);
        }

        // renewableAfter 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        ZonedDateTime renewableAfter = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES);

        // 소환사의 top 10 챔피언 플레이 정보 가져오기
        List<ChampionPlayWithChampionDto> top10ChampionPlaysDetailDtoList =
            participantService.getChampionStatus(summoner.getPuuid(), 10).stream().map(t -> ChampionPlayWithChampionDto.builder().championPlay(t).build()).toList();

        return SummonerMatchRes.builder()
            .summoner(SummonerDto.builder().summoner(summoner).build())
            .renewableAfter(renewableAfter)
            .matches(matchDtoList)
            .mostPlayed(top10ChampionPlaysDetailDtoList).build();
    }

    public List<MatchDto> get20MatchesByLastMatchId(String summonerName,
                                                    @Pattern(regexp = "^KR_\\d{10}$", message = "올바른 matchId 패턴이 아닙니다.") String lastMatchId) {

        Summoner summoner = getSummonerBySummonerName(summonerName);

        // MatchId 및 MatchDto List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchService.getSummonerMatchBySummonerPuuid(summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                // 특정 아이디 값이 최초로 작은 시점을 찾기
                .filter(matchId -> matchId.compareTo(lastMatchId) < 0)
                .limit(20).toList();

            matchDtoList = getMatchDtoList(matchIds);
        }

        return matchDtoList;
    }

    public List<MatchDto> getMatchDtoList(List<String> matchIds) {
        List<Match> matches = matchService.getMatchListByMatchIdList(matchIds);
        // Participant와 이에 대응되는 Summoner List 가져오기
        // 8월 9일 수정 : 각각의 participant는 이미 tier정보가 주어지기 때문에 summoner list에서 가져오지 않음

        List<Participant> participants = participantService.getParticipantListByMatchId(matchIds);

        Map<String, String> summonerMap = summonerRepository.findSummonersByPuuidIn(participants.stream().map(Participant::getPuuid).toList()).stream()
            .collect(Collectors.toMap(Summoner::getPuuid, s -> s.getName()));


        Map<String, List<Participant>> participantMap = participants
            .stream().collect(Collectors.groupingBy(Participant::getMatchId));

        Map<String, List<Team>> teamMap = matchService.getTeamMapMappedByMatchId(matchIds);

        return matches.stream()
            .map(match -> {
                AtomicReference<Integer> totalGold = new AtomicReference<>(0);
                List<ParticipantDto> participantDtoList = participantMap.get(match.getMatchId()).stream()
                    .map(p -> {
                        totalGold.updateAndGet(v -> v + p.getGoldEarned());

                        return ParticipantDto.builder().participant(p).summonerName(summonerMap.get(p.getPuuid())).build();
                    })
                    .toList();
                List<TeamDto> teamDtoList = teamMap.get(match.getMatchId())
                    .stream().map(t -> new TeamDto(t, totalGold.get())).toList();
                return MatchDto.builder().match(match).participants(participantDtoList).teams(teamDtoList).build();
            }).toList();
    }

    public List<SummonerPlayDto> getAllChampionPlayInfoBySummonerName(String summonerName) {
        Summoner summoner = getSummonerBySummonerName(summonerName);

        // 소환사의 모든 챔피언 플레이 정보 가져오기
        List<SummonerPlay> result = summonerPlayService.getSummonerPlaysByPuuid(summoner.getPuuid());

        return result.stream().map(SummonerPlayDto::new).toList();
    }

    // TODO 추후 시간대 체크해야 함
    public List<SoloTierWithTimeDto> getSummonerHistory(String summonerName) {
        Summoner summoner = getSummonerBySummonerName(summonerName);

        SummonerHistory history = summonerHistoryService.getSummonerHistoryByPuuid(summoner.getPuuid());

        List<SoloTierWithTimeDto> result = history.getHistory().stream()
            .map(SoloTierWithTimeDto::new).toList();

        return result;
    }


    public SummonerRankPageDto getSummonerRankByMMR(Integer page) {
        // mmr을 기준으로 내림차순하여 랭크 정보 생성
        Sort sort = Sort.by(Sort.Direction.DESC, "mmr");
        int pageSize = 100;

        int limit = 3;

        // 1. MMR 기준 Summoner page 조회
        Page<Summoner> summoners = getSummonerPage(page, sort, pageSize);
        Map<String, List<Integer>> mostChampionMap = participantService.getTopNChampionIdsByPuuids(summoners.stream().map(Summoner::getPuuid).toList(), limit);

        AtomicInteger startRank = new AtomicInteger(page * pageSize);

        // 2. 각 summoner별 랭크 정보 매기기 + most 3 champion ID 정보 가져오기
        List<SummonerRankDto> summonerRanks = summoners.stream()
            .map(s -> SummonerRankDto.builder().summoner(s).championIds(mostChampionMap.get(s.getPuuid())).rank(startRank.incrementAndGet()).build()).toList();

        return SummonerRankPageDto.builder().summonerPage(summoners).summonerRanks(summonerRanks).build();
    }

    private Page<Summoner> getSummonerPage(Integer page, Sort sort, int size) {
        Page<Summoner> summoners = summonerRepository.findAll(PageRequest.of(page, size, sort));
        return summoners;
    }


    public List<ChampionPlayWithSummonerDto> getSpecialistsByChampionName(String championName, Tier stdTier) {
        // 1. 특정 티어 이상인 소환사 정보 전부 가져오기
        Map<String, SummonerDto> summonerByTierGt = getSummonersByMmrGreaterThanEqual(stdTier).stream()
            .map(SummonerDto::new)
            .collect(Collectors.toMap(SummonerDto::getPuuid, s -> s));

        // 2. summoner puuid set 구성
        Set<String> puuids = summonerByTierGt.keySet().stream().collect(Collectors.toSet());

        AtomicInteger startRank = new AtomicInteger(0);

        // 3. DTO 엮기
        List<ChampionPlayWithSummonerDto> result = summonerPlayService.getSummonerPlaysByCondition(championName, SPECIALIST_PLAYS_CNT_LIMIT)
            .stream().filter(summonerPlay -> puuids.contains(summonerPlay.getPuuid()))
            .map(summonerPlay -> ChampionPlayWithSummonerDto.builder()
                .summonerPlay(new SummonerPlayDto(summonerPlay))
                .summoner(summonerByTierGt.get(summonerPlay.getPuuid()))
                .rank(startRank.incrementAndGet())
                .build()).toList();

        return result;
    }


    public void refreshSummoner(String puuid) {
        // 1. puuid 소환사 존재 여부 확인
        Summoner summoner = getSummonerByPuuid(puuid);

        // 2. 해당 소환사의 최종 갱신일 필드를 보고 2분이 지났는지 판단
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(); // 현재시간
        log.info("현재 : {}", now.format(formatter));
        LocalDateTime renewableAfter = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES).toLocalDateTime(); // 갱신가능 시간
        log.info("갱신가능시각 : {}", renewableAfter.format(formatter));

        // 만약 현재 시간이 최종 갱신 시점 + 120초보다 이전이라면
        if (now.isBefore(renewableAfter)) {
            throw new CustomException(ErrorCode.TOO_MANY_REQUESTS, "소환사에 대한 요청이 너무 많습니다. " + Duration.between(now, renewableAfter).getSeconds() + "초 후에 다시 시도해주세요.");
        }

        // 3. RestTemplate으로 Request 보내기
        URI uri = UriComponentsBuilder
            .fromUriString(String.format("http://%s:%s", BATCH_HOST, BATCH_PORT))
            .path("/batch/summoner/refresh/" + puuid)
            .encode()
            .build()
            .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RefreshRes> responseEntity = restTemplate.postForEntity(uri, null, RefreshRes.class);

        // 4. 결과 코드로 바로 return
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ErrorCode.SUMMONER_REFRESH_FAILED);
        }
    }

    public IngameInfoRes getIngameInfo(String summonerName) {

        Summoner summoner = getSummonerBySummonerName(summonerName);

        /*---------------------------- RestTemplate 요청 ----------------------------*/
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", RIOT_API_KEY);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
            .fromUriString("https://kr.api.riotgames.com")
            .path("/lol/spectator/v4/active-games/by-summoner/" + summoner.getSummonerId())
            .encode()
            .build()
            .toUri();

        try {

            // Request 시작
            ResponseEntity<SpectatorV4GetCurrentGameInfo> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, SpectatorV4GetCurrentGameInfo.class
            );

//            HttpStatus responseStatus = responseEntity.getStatusCode();

            SpectatorV4GetCurrentGameInfo result = responseEntity.getBody();

            // 3. SpectatorV4GetCurrentGameInfo to IngameInfoRes
            Map<String, Summoner> summonerMap = getSummonersByIds(
                result.getParticipants().stream()
                    .map(SpectatorV4GetCurrentGameInfo.CurrentGameParticipant::getSummonerId).toList()
            ).stream().collect(Collectors.toMap(Summoner::getSummonerId, s -> s));

            // summonerId 및 플레이 정보를 통해서 ChampionPlays 정보를 쿼리해야 함
            // (수정) summonerPlay를 통해서 SummonerPlays로 가져오기

//            List<IngameParticipantDto> participants = result.getParticipants().stream()
//                .map(p -> {
//                    Summoner targetSummoner = summonerMap.getOrDefault(p.getSummonerId(), null);
//                    if (targetSummoner == null) {
//                        return null;
//                    }
//                    SummonerDto summonerDto = new SummonerDto(targetSummoner);
//
//                    ChampionPlaysDto championPlaysDto = participantService.getChampionPlaysByPuuidAndChampionId(summonerDto.getPuuid(), p.getChampionId());
//
//                    return IngameParticipantDto.builder()
//                        .participant(p).summoner(summonerDto).championPlaysDto(championPlaysDto)
//                        .build();
//                }).toList();
            return null;
//            return IngameInfoRes.builder()
//                .participants(participants)
//                .gameLength(result.getGameLength())
//                .queueId(result.getGameQueueConfigId().intValue())
//                .startTime(result.getGameStartTime())
//                .build();
        } catch (HttpClientErrorException.NotFound notFoundException) {
            throw new CustomException(ErrorCode.RESULT_NOT_FOUND, "소환사 정보가 존재하지 않거나 게임 중이 아닙니다.");
        } catch (HttpClientErrorException.TooManyRequests tooManyRequestsException) {
            throw new CustomException(ErrorCode.TOO_MANY_REQUESTS);
        }

        /*---------------------------- RestTemplate 요청 ----------------------------*/
    }

    @Data
    @NoArgsConstructor
    private static class RefreshRes {
        private String message;
    }


    // 보류
    public HasPlayedRes hasPlayedTogether(String myName, String friendName) {
        // (gameId 내림차순) 내 최근 20개의 matchID로 나와 같은 팀에서

        return null;
    }

    /* --------------------------- Repository 직접 접근 메소드 --------------------------- */

    /**
     * 소환사이름을 받아서 intername으로 변환 후 DB 조회하여 소환사정보 리턴
     *
     * @param summonerName 소환사이름
     * @return Summoner
     */
    private Summoner getSummonerBySummonerName(String summonerName) {
        String internalName = keywordToInternalName(summonerName);
        return summonerRepository.findSummonerByInternalName(internalName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "summoner_name에 해당하는 소환사 데이터가 존재하지 않습니다."));
    }

    private Summoner getSummonerByPuuid(String puuid) {
        return summonerRepository.findSummonerByPuuid(puuid)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다."));
    }

    private List<Summoner> getSummonersByMmrGreaterThanEqual(Tier stdTier) {
        Integer mmr = stdTier.getBasisMMR();
        return summonerRepository.findSummonersByMmrGreaterThanEqual(mmr);
    }

    private List<Summoner> getSummonersByIds(List<String> summonerIds) {
        return summonerRepository.findSummonersBySummonerIdIn(summonerIds);
    }

    /* --------------------------- Repository 직접 접근 메소드 끝 --------------------------- */
}
