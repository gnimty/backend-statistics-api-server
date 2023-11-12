package onlysolorank.apiserver.api.service;

import static onlysolorank.apiserver.utils.CustomFunctions.keywordToInternalName;

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
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.*;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.SummonerHistory;
import onlysolorank.apiserver.domain.SummonerMatch;
import onlysolorank.apiserver.domain.SummonerPlay;
import onlysolorank.apiserver.domain.Team;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.repository.summoner.SummonerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * packageName    : onlysolorank.apiserver.api.service fileName       : SummonerService author
 * : solmin date           : 2023/07/10 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023/07/10        solmin
 * 최초 생성 2023/07/24        solmin       internalName으로 SummonerDto 조회하는 기능 구현 2023/07/28
 * solmin       keywordToInternalName 메소드를 KeywordRequestDto로 이관 2023/07/31        solmin
 * getSummonerMatchInfoBySummonerName에 필요한 DTO 구현 거의 완성 (미완료) 2023/08/09        solmin
 * getAllChampionPlayInfoByPuuid 서비스 메소드 구현 , 이전 코드 일부 활용 2023/08/09        solmin       계층적으로
 * match, team, participant에 대하여 순차적인 layer로 단계 나누기 SummonerService - SummonerMatchService -
 * ParticipantService - MatchService - ParticipantService - TeamService 2023/08/15        solmin
 * 소환사 랭크 티어 그래프 조회 메소드 추가 2023/08/15        solmin       일부 중복되는 코드 Inline 2023/08/16
 * solmin       MMR 기준 소환사 랭킹 정보 조회 메소드 추가 구현 2023/08/28        solmin       챔피언 장인랭킹 구현 2023/08/30
 * solmin       소환사 인게임 정보 구현, restTemplate로 받아온 정보 convert 및 추가정보 붙여서 보내주기 2023/09/11
 * solmin       최근 20게임 플레이한 같은 팀 소환사 리스트 가져오기 추가
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
    private final AssetService assetService;
    private final TeamService teamService;

    @Value("${batch.host}")
    private String BATCH_HOST;
    @Value("${batch.port}")
    private String BATCH_PORT;
    @Value("${riot.api-key}")
    private String RIOT_API_KEY;

    //    private static final int SPECIALIST_CNT_LIMIT = 100;
    private static final int SPECIALIST_PLAYS_CNT_LIMIT = 50; // 장인 랭킹 기준 플레이 수

    public SummonerMatchRes getSummonerMatchInfoBySummonerName(String internalName) {
        // 1. 본인 소환사 정보 가져오기
        Summoner summoner = getSummonerByName(internalName);

        // 2.MatchId 및 MatchDto List 가져오기
        List<MatchBriefRes> matches = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchService.getSummonerMatchBySummonerPuuid(
            summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                .limit(20).toList();

            matches = getMatchBriefDtoList(matchIds, summoner.getPuuid());
        }

        // renewableAfter 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        ZonedDateTime renewableAfter = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES);

        // 소환사의 top 10 챔피언 플레이 정보 가져오기
        List<SummonerPlayDto> top10ChampionPlaysDetailDtoList =
            summonerPlayService.getSummonerPlaysLimit(summoner.getPuuid(), 10).stream()
                    .map(summonerPlay -> SummonerPlayDto.from(summonerPlay))
                    .toList();

        return SummonerMatchRes.builder()
            .summoner(SummonerDto.from(summoner))
            .renewableAfter(renewableAfter)
            .matches(matches)
            .mostPlayed(top10ChampionPlaysDetailDtoList).build();
    }

    public List<MatchBriefRes> get20MatchesByOptionalLastMatchId(
        String summonerName,
        @Pattern(regexp = "^KR_\\d{10}$", message = "올바른 matchId 패턴이 아닙니다.") String lastMatchId) {

        // TODO lastMatchId 검증 필요
        Summoner summoner = getSummonerByName(summonerName);

        // MatchId 및 MatchDto List 가져오기
        List<MatchBriefRes> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchService.getSummonerMatchBySummonerPuuid(
            summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                // 특정 아이디 값이 최초로 작은 시점을 찾기
                .filter(matchId -> matchId.compareTo(lastMatchId) < 0)
                .limit(20).toList();

            matchDtoList = getMatchBriefDtoList(matchIds, summoner.getPuuid());
        }

        return matchDtoList;
    }

    public List<MatchBriefRes> getMatchBriefDtoList(List<String> matchIds, String puuid) {
        // 1. matchIds로 매치 리스트 가져오기 (List<Match>)
        List<MatchDto> matches = matchService.getMatchListByMatchIdList(matchIds);

        // 2. matchIds에 속하고 특정 puuid에 해당하는 소환사의 ParticipantBriefDto Map 가져오기 (Map<String, ParticipantBriefDto>)
        List<Participant> myParticipantInfoList = participantService.getParticipantListByMatchIdIn(
            matchIds, puuid);
        Map<String, Participant> myParticipantMap = myParticipantInfoList.stream()
            .collect(Collectors.toMap(p -> p.getMatchId(), p -> p));

        // 3. matchIds에 해당하는 모든 participant 간단 정보 가져오기
        List<ParticipantBriefDto> allParticipantBriefs = participantService.getParticipantDtoListByMatchIds(
                matchIds)
            .stream().toList();

        // 4. matchIds에 해당하는 teamMap 가져오기 (Map<String, Team>)
//        Map<String, List<Team>> teamMap = teamService.getTeamListByMatchIdList(matchIds).stream()
//            .collect(Collectors.groupingBy(Team::getMatchId));

        // 5. participants에 해당하는 summonerName 가져오기\
        Map<String, String> summonerMap = summonerRepository.findSummonersByPuuidIn(
                allParticipantBriefs.stream().map(ParticipantBriefDto::getPuuid).toList()).stream()
            .collect(Collectors.toMap(Summoner::getPuuid, s -> s.getName()));

        allParticipantBriefs.forEach(p -> p.setSummonerName(summonerMap.get(p.getPuuid())));

        Map<String, List<ParticipantBriefDto>> allParticipantsMap = allParticipantBriefs
            .stream().collect(Collectors.groupingBy(ParticipantBriefDto::getMatchId));

        return matches.stream()
            .map(match -> {
                String matchId = match.getMatchId();
                Participant target = myParticipantMap.get(matchId);

                MatchBriefRes results = MatchBriefRes.builder()
                    .participant(target != null ? ParticipantDto.builder()
                            .participant(target)
                            .summonerName(summonerMap.get(target.getPuuid()))
                            .build() : null)
                    .match(match)
                    .allParticipants(allParticipantsMap.get(matchId))
                    .build();

                return results;
            }).toList();
    }

    public MatchDetailRes getMatchDetail(String matchId) {
        MatchDto match = matchService.getMatchById(matchId)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND,
                String.format("%s에 해당하는 전적 검색 결과가 존재하지 않습니다.", matchId)));

        List<Participant> participants = participantService.getParticipantListByMatchId(matchId);

        Map<String, String> summonerMap = summonerRepository.findSummonersByPuuidIn(
                participants.stream().map(Participant::getPuuid).toList()).stream()
            .collect(Collectors.toMap(Summoner::getPuuid, Summoner::getName));

        List<Team> teams = teamService.getTeamListByMatchId(matchId);

        List<ParticipantDto> participantDtoList = participants.stream()
            .map(p -> ParticipantDto.builder().participant(p)
                .summonerName(summonerMap.get(p.getPuuid())).build())
            .toList();

        List<TeamDto> teamDtoList = teams.stream()
            .map(t -> {
                Integer teamId = t.getTeamId();

                AtomicReference<Integer> totalGold = new AtomicReference<>(0);

                participants.stream().filter(p -> p.getTeamId().equals(teamId)).forEach(p -> {
                    totalGold.updateAndGet(v -> v + p.getGoldEarned());
                });

                return new TeamDto(t, totalGold.get());
            }).toList();

        return MatchDetailRes.builder().match(match).participants(participantDtoList)
            .teams(teamDtoList).build();
    }


    public List<SummonerPlayDto> getAllChampionPlayInfoBySummonerName(String summonerName) {
        Summoner summoner = getSummonerByName(summonerName);

        // 소환사의 모든 챔피언 플레이 정보 가져오기
        List<SummonerPlay> result = summonerPlayService.getSummonerPlaysByPuuid(
            summoner.getPuuid());

        return result.stream()
                .map(summonerPlay -> SummonerPlayDto.from(summonerPlay))
                .toList();
    }

    // TODO 추후 시간대 체크해야 함
    public List<SoloTierDto> getSummonerHistory(String summonerName) {
        Summoner summoner = getSummonerByName(summonerName);

        SummonerHistory history = summonerHistoryService.getSummonerHistoryByPuuid(
            summoner.getPuuid());

        List<SoloTierDto> result = history.getHistory().stream()
            .map(h -> SoloTierDto.from(h))
                .toList();

        return result;
    }


    public SummonerRankPageRes getSummonerRankByMMR(Integer page) {
        // mmr을 기준으로 내림차순하여 랭크 정보 생성
        Sort sort = Sort.by(Sort.Direction.DESC, "mmr");
        int pageSize = 100;
        page = page-1;

        // 1. MMR 기준 Summoner page 조회
        Page<Summoner> summoners = getSummonerPage(page, sort, pageSize);

        AtomicInteger startRank = new AtomicInteger(page * pageSize);


        // 2. 각 summoner별 랭크 정보 매기기 + most 3 champion ID 정보 가져오기
        List<SummonerRankDto> summonerRanks = summoners.stream()
            .map(s -> SummonerRankDto.builder()
                .summoner(s)
                .rank(startRank.incrementAndGet())
                .build())
            .toList();

        return SummonerRankPageRes.builder().summonerPage(summoners).summonerRanks(summonerRanks)
            .build();
    }



    public List<SummonerPlayWithSummonerDto> getSpecialistsByChampionName(String championName,
                                                                          Tier stdTier) {
        // 1. 특정 티어 이상인 소환사 정보 전부 가져오기
        Map<String, SummonerDto> summonerByTierGt = getSummonersByMmrGreaterThanEqual(stdTier).stream()
            .map(summoner -> SummonerDto.from(summoner))
            .collect(Collectors.toMap(SummonerDto::getPuuid, s -> s));

        // 2. summoner puuid set 구성
        Set<String> puuids = summonerByTierGt.keySet().stream().collect(Collectors.toSet());

        AtomicInteger startRank = new AtomicInteger(0);

        // 3. DTO 엮기
        List<SummonerPlayWithSummonerDto> result = summonerPlayService.getSummonerPlaysByCondition(
                championName, SPECIALIST_PLAYS_CNT_LIMIT)
            .stream().filter(summonerPlay -> puuids.contains(summonerPlay.getPuuid()))
            .map(summonerPlay -> SummonerPlayWithSummonerDto.builder()
                .summonerPlay(SummonerPlayDto.from(summonerPlay))
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
        LocalDateTime renewableAfter = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES)
            .toLocalDateTime(); // 갱신가능 시간
        log.info("갱신 가능 시각 : {}", renewableAfter.format(formatter));

        // 만약 현재 시간이 최종 갱신 시점 + 120초보다 이전이라면
        if (now.isBefore(renewableAfter)) {
            throw new CustomException(ErrorCode.TOO_MANY_REQUESTS,
                String.format("소환사에 대한 요청이 너무 많습니다. %s초 후에 다시 시도해주세요.",
                    Duration.between(now, renewableAfter).getSeconds()));
        }

        // 3. RestTemplate으로 Request 보내기
        URI uri = UriComponentsBuilder
            .fromUriString(String.format("http://%s:%s", BATCH_HOST, BATCH_PORT))
            .path("/batch/summoner/refresh/" + puuid)
            .encode()
            .build()
            .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RefreshRes> responseEntity = restTemplate.postForEntity(uri, null,
            RefreshRes.class);

        // 4. 결과 코드로 바로 return
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ErrorCode.SUMMONER_REFRESH_FAILED);
        }
    }


    public CurrentGameRes getCurrentGame(String summonerName) {

        Summoner summoner = getSummonerByName(summonerName);

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
            ResponseEntity<SpectatorV4CurrentGameDto> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    httpEntity,
                    SpectatorV4CurrentGameDto.class
            );

            SpectatorV4CurrentGameDto result = responseEntity.getBody();

            List<String> summonerIds = result.getParticipants().stream()
                    .map(SpectatorV4CurrentGameDto.CurrentGameParticipant::getSummonerId)
                    .toList();

            // 인게임 조회 정보 내에 있는 summonerId와 소환사 정보쌍을 담은 Map 생성
            Map<String, Summoner> summonerMap = getSummonersByIds(summonerIds).stream()
                    .collect(Collectors.toMap(Summoner::getSummonerId, s -> s));

            // SpectatorV4CurrentGameDto에서 CurrentGameRes로 변환하기 시작
            Map<String, Long> championIdPairs = new HashMap<>();

            result.getParticipants().forEach(participant -> {
                Summoner targetSummoner = summonerMap.getOrDefault(participant.getSummonerId(), null);
                if (targetSummoner != null) {
                    championIdPairs.put(targetSummoner.getPuuid(), participant.getChampionId());
                }
            });


            Map<String, SummonerPlay> collect = summonerPlayService.getSummonerPlaysByPairs(
                            championIdPairs).stream()
                .collect(Collectors.toMap(s -> s.getPuuid(), s -> s));

            List<CurrentGameParticipantDto> participants = result.getParticipants().stream().map(p -> {
                SummonerPlayDto summonerPlayDto = null;
                Summoner targetSummoner = summonerMap.get(p.getSummonerId());

                if (targetSummoner != null) {
                    SummonerPlay summonerPlay = collect.get(targetSummoner.getPuuid());
                    if (summonerPlay != null) {
                        summonerPlayDto = SummonerPlayDto.from(summonerPlay);
                    }
                }

                ChampionDto champion = assetService.getChampion(p.getChampionId());

                return CurrentGameParticipantDto.builder()
                    .summonerPlayDto(summonerPlayDto)
                    .champion(champion)
                    .participant(p)
                    .summoner(SummonerDto.from(targetSummoner))
                    .build();
            }).toList();

            return CurrentGameRes.builder()
                .participants(participants)
                .gameLength(result.getGameLength())
                .queueId(result.getGameQueueConfigId().intValue())
                .startTime(result.getGameStartTime())
                .build();

        } catch (HttpClientErrorException.NotFound notFoundException) {
            throw new CustomException(ErrorCode.RESULT_NOT_FOUND, "현재 소환사가 게임 중이 아닙니다.");
        } catch (HttpClientErrorException.TooManyRequests tooManyRequestsException) {
            throw new CustomException(ErrorCode.TOO_MANY_REQUESTS);
        }

    }

    public List<RecentMemberDto> getRecentMemberInfo(String summonerName) {
        Summoner summoner = getSummonerByName(summonerName);

        List<RecentMemberDto> result = participantService.getDistinctTeamMembersExceptMe(
            summoner.getPuuid());
        return result;
    }


    /* --------------------------- Repository 직접 접근 메소드 --------------------------- */

    /**
     * 소환사이름을 받아서 internalName으로 변환 후 DB 조회하여 소환사정보 리턴
     *
     * @param summonerName 소환사이름
     * @return Summoner
     */
    public Summoner getSummonerByName(String summonerName) {
        String internalName = keywordToInternalName(summonerName);
        return summonerRepository.findSummonerByInternalName(internalName)
                .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND,
                        "summoner_name에 해당하는 소환사 데이터가 존재하지 않습니다."));
    }

    private Summoner getSummonerByPuuid(String puuid) {
        return summonerRepository.findSummonerByPuuid(puuid)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND,
                "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다."));
    }

    private List<Summoner> getSummonersByMmrGreaterThanEqual(Tier stdTier) {
        Integer mmr = stdTier.getBasisMMR();
        return summonerRepository.findSummonersByMmrGreaterThanEqual(mmr);
    }

    private List<Summoner> getSummonersByIds(List<String> summonerIds) {
        return summonerRepository.findSummonersBySummonerIdIn(summonerIds);
    }

    private Page<Summoner> getSummonerPage(Integer page, Sort sort, int size) {
        Page<Summoner> summoners = summonerRepository.findAll(PageRequest.of(page, size, sort));
        return summoners;
    }

    public List<SummonerDto> getTop5SummonersByInternalName(String internalName) {
        return summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
                .stream().map(summoner->SummonerDto.from(summoner))
                .toList();
    }

    @Data
    @NoArgsConstructor
    private static class RefreshRes {

        private String message;
    }

    /* --------------------------- Repository 직접 접근 메소드 끝 --------------------------- */
}
