package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import onlysolorank.apiserver.api.controller.dto.SummonerMatchResponseDto;

import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.domain.*;
import onlysolorank.apiserver.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
 *                                SummonerService
 *                                  - SummonerMatchService
 *                                  - ParticipantService
 *                                  - MatchService
 *                                     - ParticipantService
 *                                     - TeamService
 * 2023/08/15        solmin       소환사 랭크 티어 그래프 조회 메소드 추가
 * 2023/08/15        solmin       일부 중복되는 코드 Inline
 * 2023/08/16        solmin       MMR 기준 소환사 랭킹 정보 조회 메소드 추가 구현

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

    public SummonerMatchResponseDto getSummonerMatchInfoBySummonerName(String summonerName) {

        Summoner summoner = getSummonerBySummonerName(summonerName);

        // MatchId 및 MatchDto List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchService.getSummonerMatchBySummonerPuuid(summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                    .limit(20).toList();
            matchDtoList = getMatchDtoList(matchIds);
        }

        // renewableAt 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        LocalDateTime renewableAt = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES);

        // 소환사의 top 10 챔피언 플레이 정보 가져오기
        List<ChampionPlaysBriefDto> top10ChampionPlaysBriefDtoList =
            participantService.getChampionStatus(summoner.getPuuid(), 10);

        return SummonerMatchResponseDto.builder()
            .summoner(SummonerDto.builder().summoner(summoner).build())
            .renewableAt(renewableAt)
            .matches(matchDtoList)
            .mostPlayed(top10ChampionPlaysBriefDtoList).build();
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
        Map<String, List<Participant>> participantMap =
            participantService.getParticipantListByMatchId(matchIds)
                .stream().collect(Collectors.groupingBy(Participant::getMatchId));

        Map<String, List<Team>> teamMap = matchService.getTeamMapMappedByMatchId(matchIds);

        return matches.stream()
            .map(match -> {
                List<ParticipantDto> participantDtoList = participantMap.get(match.getMatchId()).stream()
                    .map(p -> ParticipantDto.builder().participant(p).build())
                    .toList();
                List<TeamDto> teamDtoList = teamMap.get(match.getMatchId())
                    .stream().map(TeamDto::new).toList();
                return MatchDto.builder().match(match).participants(participantDtoList).teams(teamDtoList).build();
            }).toList();
    }

    public List<ChampionPlaysBriefDto> getAllChampionPlayInfoByPuuid(String puuid) {

        if(summonerRepository.existsByPuuid(puuid)){
            // 소환사의 모든 챔피언 플레이 정보 가져오기
            List<ChampionPlaysBriefDto> championPlaysBriefDtoList =
                participantService.getChampionStatus(puuid, LIMIT);
            return championPlaysBriefDtoList;
        }else{
            throw new CustomException(ErrorCode.RESULT_NOT_FOUND, "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다.");
        }
    }

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
            .map(s -> {
                int rank = startRank.incrementAndGet();
                return SummonerRankDto.builder().summoner(s).championIds(mostChampionMap.get(s.getPuuid())).rank(rank).build();
            }).toList();

        return SummonerRankPageDto.builder().summonerPage(summoners).summonerRanks(summonerRanks).build();
    }

    private Page<Summoner> getSummonerPage(Integer page, Sort sort, int size) {
        Page<Summoner> summoners = summonerRepository.findAll(PageRequest.of(page, size, sort));
        return summoners;
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

    public List<SummonerDto> getSummonerDtoListByInternalName(String internalName) {
        // internal name으로 찾기

        return summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build())
            .toList();
    }
    /* --------------------------- Repository 직접 접근 메소드 --------------------------- */
}
