package onlysolorank.apiserver.api.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import onlysolorank.apiserver.api.controller.dto.HasPlayedRes;
import onlysolorank.apiserver.api.controller.dto.SummonerMatchRes;

import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.domain.*;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

import static onlysolorank.apiserver.utils.CustomConverter.keywordToInternalName;


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
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerService {

    // 모든 챔피언 범위 내의 검색 결과 가져오기 위해서 큰 숫자로 세팅
    public static final int LIMIT = 2000;
    private final SummonerRepository summonerRepository;
    private final SummonerMatchService summonerMatchService;
    private final MatchService matchService;
    private final ParticipantService participantService;

    @Value("${batch.host}")
    private String BATCH_HOST;

    @Value("${batch.port}")
    private String BATCH_PORT;

    public SummonerMatchRes getSummonerMatchInfoByInternalName(String internalName) {

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
            participantService.getChampionStatus(summoner.getPuuid(), 10).stream().map(t->ChampionPlayWithChampionDto.builder().championPlay(t).build()).toList();

        return SummonerMatchRes.builder()
            .summoner(SummonerDto.builder().summoner(summoner).build())
            .renewableAfter(renewableAfter)
            .matches(matchDtoList)
            .mostPlayed(top10ChampionPlaysDetailDtoList).build();
    }

    public List<MatchDto> get20MatchesByLastMatchId(String summonerName, String lastMatchId) {
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

        Map<String, String> summonerMap = summonerRepository.findSummonersByPuuidInCustom(participants.stream().map(Participant::getPuuid).toList()).stream()
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
                    .stream().map(t-> new TeamDto(t, totalGold.get())).toList();
                return MatchDto.builder().match(match).participants(participantDtoList).teams(teamDtoList).build();
            }).toList();
    }

    public List<ChampionPlayWithChampionDto> getAllChampionPlayInfoByPuuid(String internalName) {
        Summoner summoner = summonerRepository.findOneByInternalName(internalName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "summoner_name에 해당하는 소환사 데이터가 존재하지 않습니다."));

        // 소환사의 모든 챔피언 플레이 정보 가져오기
        return participantService.getChampionStatus(summoner.getPuuid(), LIMIT).stream().map(s->ChampionPlayWithChampionDto.builder().championPlay(s).build()).toList();
    }

    // TODO 추후 시간대 체크해야 함
    public List<SoloTierWithTimeDto> getSummonerHistory(String puuid) {
        Summoner summoner = getSummonerByPuuid(puuid);

        return summoner.getHistory().stream()
            .map(h -> SoloTierWithTimeDto.builder().history(h).build()).toList();
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
        Map<String, Summoner> summonerByTierGt = summonerRepository.findSummonersByMmrGreaterThanEqual(stdTier.getBasisMMR()).stream()
            .collect(Collectors.toMap(Summoner::getPuuid, s->s));

        List<String> puuids = summonerByTierGt.keySet().stream().toList();

        // 2. participants 정보에서 특정 championName과 puuid 정보가 담긴 정보를 필터링하여 가져오기
        List<ChampionPlaysDetailDto> result = participantService.getSpecialistsByCondition(puuids, championName);

        AtomicInteger startRank = new AtomicInteger(0);
        // 3. DTO 엮기
        List<ChampionPlayWithSummonerDto> specialists = result.stream().map(s -> ChampionPlayWithSummonerDto.builder()
                .championPlay(s)
                .summoner(new SummonerDto(summonerByTierGt.get(s.getPuuid())))
                .rank(startRank.incrementAndGet())
                .build())
            .toList();
        return specialists;
    }

    public void refreshSummoner(String puuid) {
        // 1. puuid 소환사 존재 여부 확인
        Summoner summoner = summonerRepository.findOneByPuuid(puuid)
            .orElseThrow(()-> new CustomException(ErrorCode.RESULT_NOT_FOUND, "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다."));

        // 2. 해당 소환사의 최종 갱신일 필드를 보고 2분이 지났는지 판단

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(); // 현재시간
        log.info("현재 : {}",now.format(formatter));
        LocalDateTime renewableAfter = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES).toLocalDateTime(); // 갱신가능 시간
        log.info("갱신가능시각 : {}",renewableAfter.format(formatter));

        // 만약 현재 시간이 최종 갱신 시점 + 120초보다 이전이라면
        if (now.isBefore(renewableAfter)) {
            throw new CustomException(ErrorCode.TOO_MANY_REQUESTS, "소환사에 대한 요청이 너무 많습니다. "+Duration.between(now, renewableAfter).getSeconds()+"초 후에 다시 시도해주세요.");
        }

        // 3. RestTemplate으로 Request 보내기
        URI uri = UriComponentsBuilder
            .fromUriString(String.format("http://%s:%s", BATCH_HOST, BATCH_PORT))
            .path("/batch/summoner/refresh/" + summoner.getInternalName())
            .encode()
            .build()
            .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RefreshRes> responseEntity = restTemplate.postForEntity(uri, null, RefreshRes.class);

        // 4. 결과 코드로 바로 return
        if (responseEntity.getStatusCode()!= HttpStatus.OK){
            throw new CustomException(ErrorCode.SUMMONER_REFRESH_FAILED);
        }
    }

    @Data
    @NoArgsConstructor
    private static class RefreshRes{
        private String message;
    }

    // 보류
    public HasPlayedRes hasPlayedTogether(String myName, String friendName) {
        // (gameId 내림차순) 내 최근 20개의 matchID로 나와 같은 팀에서

        return null;
    }

    /* --------------------------- Repository 직접 접근 메소드 --------------------------- */
    private Summoner getSummonerBySummonerName(String summonerName) {
        String internalName = keywordToInternalName(summonerName);
        return summonerRepository.findOneByInternalName(internalName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "summoner_name에 해당하는 소환사 데이터가 존재하지 않습니다."));
    }

    private Summoner getSummonerByPuuid(String puuid) {
        return summonerRepository.findOneByPuuid(puuid)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다."));
    }

    private List<Summoner> getSummonerByMMRGt(Tier stdTier) {
        return new ArrayList<>();
    }


    public List<SummonerDto> getSummonerDtoListByInternalName(String internalName) {
        // internal name으로 찾기

        return summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build())
            .toList();
    }

    /* --------------------------- Repository 직접 접근 메소드 --------------------------- */
}
