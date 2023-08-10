package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.SummonerMatchResponseDto;
import onlysolorank.apiserver.api.dto.*;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.domain.*;
import onlysolorank.apiserver.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerService {

    // 모든 챔피언 범위 내의 검색 결과 가져오기 위해서 큰 숫자로 세팅
    public static final int LIMIT = 2000;
    private final SummonerRepository summonerRepository;
    private final SummonerMatchRepository summonerMatchRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final ParticipantRepository participantRepository;

    public List<SummonerDto> getSummonerDtoListByInternalName(String internalName) {
        // internal name으로 찾기
        List<SummonerDto> result = summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build())
            .toList();

        return result;
    }

    public SummonerMatchResponseDto getSummonerMatchInfoBySummonerName(String summonerName) {

        Summoner summoner = getSummonerBySummonerName(summonerName);

        // MatchId 및 MatchDto List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchRepository.findOneByPuuid(summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                    .limit(20).toList();
            matchDtoList = getMatchDtoList(matchIds);
        }

        // renewableAt 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        LocalDateTime renewableAt = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES);

        // 소환사의 top 10 챔피언 플레이 정보 가져오기
        List<ChampionPlaysBriefDto> championPlaysBriefDtoList = participantRepository.findTopChampionStatsByPuuid(summoner.getPuuid(), 10);

        return SummonerMatchResponseDto.builder()
            .summoner(SummonerDto.builder().summoner(summoner).build())
            .renewableAt(renewableAt)
            .matches(matchDtoList)
            .mostPlayed(championPlaysBriefDtoList).build();
    }

    public List<MatchDto> get20MatchesByLastMatchId(String summonerName, String lastMatchId) {
        Summoner summoner = getSummonerBySummonerName(summonerName);

        // MatchId 및 MatchDto List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchRepository.findOneByPuuid(summoner.getPuuid());

        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                // 특정 아이디 값이 최초로 작은 시점을 찾기
                .filter(matchId -> matchId.compareTo(lastMatchId) < 0)
                .limit(20).toList();

            matchDtoList = getMatchDtoList(matchIds);
        }

        return matchDtoList;
    }

    private Summoner getSummonerBySummonerName(String summonerName) {
        String internalName = keywordToInternalName(summonerName);
        Summoner summoner = summonerRepository.findOneByInternalName(internalName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "summoner_name에 해당하는 소환사 데이터가 존재하지 않습니다."));
        return summoner;
    }

    private Summoner getSummonerByPuuid(String puuid) {
        Summoner summoner = summonerRepository.findOneByPuuid(puuid)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다."));
        return summoner;
    }

    public List<MatchDto> getMatchDtoList(List<String> matchIds){
        List<Match> matches = matchRepository.findByMatchIdInCustom(matchIds);
        // Participant와 이에 대응되는 Summoner List 가져오기
        // 8월 9일 수정 : 각각의 participant는 이미 tier정보가 주어지기 때문에 summoner list에서 가져오지 않음
        Map<String, List<Participant>> participantMap =
            participantRepository.findByMatchIdInCustom(matchIds).stream()
                .collect(Collectors.groupingBy(Participant::getMatchId));

        Map<String, List<Team>> teamMap =
            teamRepository.findByMatchIdInCustom(matchIds).stream()
                .collect(Collectors.groupingBy(Team::getMatchId));

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
            List<ChampionPlaysBriefDto> championPlaysBriefDtoList = participantRepository.findTopChampionStatsByPuuid(puuid, LIMIT);
            return championPlaysBriefDtoList;
        }else{
            throw new CustomException(ErrorCode.RESULT_NOT_FOUND, "서버 내부에 해당 puuid를 가진 소환사 데이터가 존재하지 않습니다.");
        }
    }
}
