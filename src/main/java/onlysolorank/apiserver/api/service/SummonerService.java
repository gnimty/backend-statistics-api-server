package onlysolorank.apiserver.api.service;

import static onlysolorank.apiserver.utils.CustomFunctions.keywordToInternalTagName;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.CurrentGameRes;
import onlysolorank.apiserver.api.controller.dto.MatchBriefRes;
import onlysolorank.apiserver.api.controller.dto.MatchDetailRes;
import onlysolorank.apiserver.api.controller.dto.SummonerMatchRes;
import onlysolorank.apiserver.api.controller.dto.SummonerRankPageRes;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.ChampionDto;
import onlysolorank.apiserver.api.service.dto.CurrentGameParticipantDto;
import onlysolorank.apiserver.api.service.dto.MatchDto;
import onlysolorank.apiserver.api.service.dto.MatchSummaryDto;
import onlysolorank.apiserver.api.service.dto.ParticipantBriefDto;
import onlysolorank.apiserver.api.service.dto.ParticipantDto;
import onlysolorank.apiserver.api.service.dto.RecentMemberDto;
import onlysolorank.apiserver.api.service.dto.SummonerNameDto;
import onlysolorank.apiserver.api.service.dto.SummonerTierDto;
import onlysolorank.apiserver.api.service.dto.SpectatorV4CurrentGameDto;
import onlysolorank.apiserver.api.service.dto.SummonerDto;
import onlysolorank.apiserver.api.service.dto.SummonerPlayDto;
import onlysolorank.apiserver.api.service.dto.SummonerPlayWithSummonerDto;
import onlysolorank.apiserver.api.service.dto.SummonerRankDto;
import onlysolorank.apiserver.api.service.dto.TeamDto;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.SummonerHistory;
import onlysolorank.apiserver.domain.SummonerMatch;
import onlysolorank.apiserver.domain.summoner_play.BaseSummonerPlay;
import onlysolorank.apiserver.domain.Team;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.repository.summoner.SummonerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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


    public SummonerMatchRes getSummonerMatchInfoBySummonerName(String internalTagName, QueueType queueType) {
        // 1. 본인 소환사 정보 가져오기
        Summoner summoner = getSummonerByInternalTagName(internalTagName);

        // 2.MatchId 및 MatchDto List 가져오기
        List<String> matchIds = summonerMatchService.getSummonerMatchIdsBySummonerPuuidAndQueueType(
            summoner.getPuuid(), queueType).stream()
            .limit(20).toList();

        List<MatchBriefRes> matches = getMatchBriefDtoList(matchIds, summoner.getPuuid());

        MatchSummaryDto matchSummary = MatchSummaryDto.from(matches.subList(0, Math.min(20, matches.size())));

        // renewableAfter 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        LocalDateTime renewableAfter = summoner.getUpdatedAt()
            .plus(2, ChronoUnit.MINUTES).toLocalDateTime();
//            .minus(9, ChronoUnit.HOURS);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = renewableAfter.format(formatter);

        // 결과 출력
        System.out.println("Formatted DateTime: " + formattedDateTime);


        SummonerDto summonerInfo = SummonerDto.from(summoner);


        // 마스터 티어 이상의 유저라면 랭크 정보 추가

        if (Objects.nonNull(summonerInfo.getSoloTierInfo()) && summonerInfo.getSoloTierInfo().getTier().getBasisMMR()>= Tier.master.getBasisMMR()){
            Integer rank = getSummonerRanks(summonerInfo.getSoloTierInfo().getMmr(), QueueType.RANK_SOLO);
            summonerInfo.getSoloTierInfo().setRank(rank);
        }

        if (Objects.nonNull(summonerInfo.getFlexTierInfo()) && summonerInfo.getFlexTierInfo().getTier().getBasisMMR()>= Tier.master.getBasisMMR()){
            Integer rank = getSummonerRanks(summonerInfo.getFlexTierInfo().getMmr(), QueueType.RANK_FLEX);
            summonerInfo.getFlexTierInfo().setRank(rank);
        }




        return SummonerMatchRes.builder()
            .summoner(summonerInfo)
            .renewableAfter(renewableAfter)
            .matches(matches)
            .matchSummary(matchSummary)
            .build();
    }

    public List<MatchBriefRes> get20MatchesByOptionalLastMatchId(
        String internalTagName,
        @Pattern(regexp = "^KR_\\d{10}$", message = "올바른 matchId 패턴이 아닙니다.") String lastMatchId,
        QueueType queueType) {

        // TODO lastMatchId 검증 필요
        Summoner summoner = getSummonerByInternalTagName(internalTagName);

        // MatchId 및 MatchDto List 가져오기
        List<String> matchIds = summonerMatchService.getSummonerMatchIdsBySummonerPuuidAndQueueType(
                summoner.getPuuid(), queueType).stream()
            .filter(matchId -> matchId.compareTo(lastMatchId) < 0)
            .limit(20).toList();

        List<MatchBriefRes> matchDtoList = getMatchBriefDtoList(matchIds, summoner.getPuuid());



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

        // 5. participants에 해당하는 summonerName 가져오기
        Map<String, SummonerNameDto> summonerMap = new HashMap<>();

        List<SummonerNameDto> foundSummoners = summonerRepository.findSummonerNameDtosByPuuidIn(
                allParticipantBriefs.stream().map(ParticipantBriefDto::getPuuid).toList());

        for(SummonerNameDto summoner : foundSummoners){
            if(!summonerMap.containsKey(summoner.getPuuid())){
                summonerMap.put(summoner.getPuuid(), summoner);
            }
        }

        allParticipantBriefs.forEach(p -> {
            SummonerNameDto target = summonerMap.get(p.getPuuid());
            if (Objects.nonNull(target)) {
                p.setSummonerName(target.getName());
                p.setInternalTagName(target.getInternalTagName());
                p.setTagLine(target.getTagLine());
            }
        });

        Map<String, List<ParticipantBriefDto>> allParticipantsMap = allParticipantBriefs
            .stream().collect(Collectors.groupingBy(ParticipantBriefDto::getMatchId));

        return matches.stream()
            .map(match -> {
                String matchId = match.getMatchId();
                Participant targetParticipant = myParticipantMap.get(matchId);
                SummonerNameDto targetSummoner = summonerMap.get(targetParticipant.getPuuid());

                MatchBriefRes results = MatchBriefRes.builder()
                    .participant(targetParticipant != null ? ParticipantDto.builder()
                        .participant(targetParticipant)
                        .summonerName(targetSummoner != null ? targetSummoner.getName(): null)
                        .tagLine(targetSummoner.getTagLine())
                        .internalTagName(targetSummoner.getInternalTagName())
                        .build() : null)
                    .matchInfo(match)
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

        Map<String, SummonerNameDto> summonerMap = summonerRepository.findSummonerNameDtosByPuuidIn(
                participants.stream().map(Participant::getPuuid).toList()).stream()
            .collect(Collectors.toMap(SummonerNameDto::getPuuid, s->s));

        List<Team> teams = teamService.getTeamListByMatchId(matchId);

        List<ParticipantDto> participantDtoList = participants.stream()
            .map(p -> {
                SummonerNameDto summonerNameDto = summonerMap.get(p.getPuuid());
                if (Objects.isNull(summonerNameDto)){
                    return ParticipantDto.builder().participant(p)
                        .build();
                }
                return ParticipantDto.builder().participant(p)
                    .summonerName(summonerNameDto.getName())
                    .internalTagName(summonerNameDto.getInternalTagName())
                    .tagLine(summonerNameDto.getTagLine())
                    .build();
            })
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


    public List<SummonerPlayDto> getChampionPlayInfo(String summonerTagName, QueueType queueType, Boolean brief) {
        Summoner summoner = getSummonerByInternalTagName(summonerTagName);

        List<? extends BaseSummonerPlay> result = summonerPlayService.getSummonerPlaysByPuuid(summoner.getPuuid(), queueType, brief);

        return result.stream()
            .map(s -> SummonerPlayDto.from(s))
            .toList();
    }

    // TODO 추후 시간대 체크해야 함
    public List<SummonerTierDto> getSummonerHistory(String summonerName) {
        Summoner summoner = getSummonerByInternalTagName(summonerName);

        SummonerHistory history = summonerHistoryService.getSummonerHistoryByPuuid(
            summoner.getPuuid());

        List<SummonerTierDto> result = history.getHistory().stream()
            .map(h -> SummonerTierDto.from(h))
            .toList();

        return result;
    }


    public SummonerRankPageRes getSummonerRankByMMR(Integer page, QueueType queueType,  int pageSize) {
        // mmr을 기준으로 내림차순하여 랭크 정보 생성
        Sort sort = Sort.by(Sort.Direction.DESC, "mmr");

        if (queueType==QueueType.RANK_FLEX){
            sort = Sort.by(Sort.Direction.DESC, "mmr_flex");
        }


        page = page - 1;

        // 1. MMR 기준 Summoner page 조회
        Page<Summoner> summoners = summonerRepository.findSummonerPage(page, pageSize, sort);

        AtomicInteger startRank = new AtomicInteger(page * pageSize);

        // 2. 각 summoner별 랭크 정보 매기기 + most 3 champion ID 정보 가져오기
        List<SummonerRankDto> summonerRanks = summoners.stream()
            .map(s -> SummonerRankDto.builder()
                .queueType(queueType)
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
//        List<SummonerPlayWithSummonerDto> result = summonerPlayService.getSummonerPlaysByCondition(
//                championName, SPECIALIST_PLAYS_CNT_LIMIT)
//            .stream().filter(summonerPlay -> puuids.contains(summonerPlay.getPuuid()))
//            .map(summonerPlay -> SummonerPlayWithSummonerDto.builder()
//                .summonerPlay(SummonerPlayDto.from(summonerPlay))
//                .summoner(summonerByTierGt.get(summonerPlay.getPuuid()))
//                .rank(startRank.incrementAndGet())
//                .build()).toList();

//        return result;
        return null;
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
            .path("/refresh/summoner/" + puuid)
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


    public CurrentGameRes getCurrentGame(String internalTagName) {

        Summoner summoner = getSummonerByInternalTagName(internalTagName);

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

            QueueType queueType = QueueType.RANK_SOLO;

            if (result.getGameQueueConfigId().intValue()==QueueType.RANK_FLEX.getQueueId()){
                queueType = QueueType.RANK_FLEX;
            }

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
            Map<String, BaseSummonerPlay> collect = summonerPlayService.getSummonerPlaysByPairs(championIdPairs, queueType).stream()
                .collect(Collectors.toMap(s -> s.getPuuid(), s -> s));
            List<CurrentGameParticipantDto> participants = result.getParticipants().stream().map(p -> {
                SummonerPlayDto summonerPlayDto = null;
                Summoner targetSummoner = summonerMap.get(p.getSummonerId());

                if (targetSummoner != null) {
                    BaseSummonerPlay summonerPlay = collect.get(targetSummoner.getPuuid());
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

    public List<RecentMemberDto> getRecentMemberInfo(String internalTagName, QueueType queueType) {
        Summoner summoner = getSummonerByInternalTagName(internalTagName);

        List<RecentMemberDto> result = Optional.ofNullable(participantService.getDistinctTeamMembersByQueueTypeExceptMe(
            summoner.getPuuid(), queueType)).orElse(new ArrayList<>());

        return result;
    }

    public Integer getSummonerRanks(Integer mmr, QueueType queueType){
        if(queueType==QueueType.RANK_FLEX){
            return 1+summonerRepository.countByMmrFlexGreaterThan(mmr);
        }

        return 1+summonerRepository.countByMmrGreaterThan(mmr);
    }


    /* --------------------------- Repository 직접 접근 메소드 --------------------------- */

    /**
     * 소환사이름을 받아서 internalTagName으로 변환 후 DB 조회하여 소환사정보 리턴
     *
     * @param internalTagName 소환사이름
     * @return Summoner
     */
    public Summoner getSummonerByInternalTagName(String internalTagName) {
        return summonerRepository.findSummonerByInternalTagName(internalTagName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND,
                "summoner tagname에 해당하는 소환사 데이터가 존재하지 않습니다."));
    }

    private Summoner getSummonerByPuuid(String puuid) {
        return summonerRepository.findSummonerByPuuid(puuid)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND,
                "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다."));
    }

    public List<Summoner> getSummonersByPuuidIn(List<String> puuids){
        return summonerRepository.findSummonersByPuuidIn(puuids);
    }

    private List<Summoner> getSummonersByMmrGreaterThanEqual(Tier stdTier) {
        Integer mmr = stdTier.getBasisMMR();
        return summonerRepository.findSummonersByMmrGreaterThanEqual(mmr);
    }

    private List<Summoner> getSummonersByIds(List<String> summonerIds) {
        return summonerRepository.findSummonersBySummonerIdIn(summonerIds);
    }


    public List<SummonerDto> getTop5SummonersByInternalTagName(String internalTagName) {
        return summonerRepository.findTop5ByInternalTagNameStartsWithOrderByInternalTagNameAscMmrDesc(internalTagName)
            .stream().map(summoner -> SummonerDto.from(summoner))
            .toList();
    }

    @Data
    @NoArgsConstructor
    private static class RefreshRes {

        private String message;
    }

    /* --------------------------- Repository 직접 접근 메소드 끝 --------------------------- */
}
