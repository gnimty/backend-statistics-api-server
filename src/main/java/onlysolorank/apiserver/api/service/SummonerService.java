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
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerService {

    private final SummonerRepository summonerRepository;
    private final SummonerMatchRepository summonerMatchRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final ParticipantRepository participantRepository;

    public List<SummonerDto> getSummonerByInternalName(String internalName) {
        // internal name으로 찾기
        List<SummonerDto> result = summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build())
            .toList();

        return result;
    }

    public SummonerMatchResponseDto getSummonerMatchInfoBySummonerName(String summonerName) {

        // SummonerDto, DateTime, ChampionPlaysBriefDto, MatchDto
        Summoner summoner = getSummoner(summonerName);

        // 3. MatchId List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchRepository.findOneByPuuid(summoner.getPuuid());

        // 4-1. Match List 가져오기
        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                    .limit(20).toList();
            matchDtoList = getMatchDtoList(matchIds);
        }

        // 2. datetime 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        LocalDateTime renewableAt = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES);
        // 5. summoner의 most top3 play 챔피언 정보 가져오기
        List<ChampionPlaysBriefDto> ChampionPlaysBriefDtoList = participantRepository.findTop10ChampionStatsByPuuid(summoner.getPuuid());

        return SummonerMatchResponseDto.builder()
            .summoner(SummonerDto.builder().summoner(summoner).build())
            .renewableAt(renewableAt)
            .matches(matchDtoList)
            .mostPlayed(ChampionPlaysBriefDtoList).build();
    }

    public List<MatchDto> getOnly20MatchByLastMatchId(String summonerName, String lastMatchId) {
        Summoner summoner = getSummoner(summonerName);

        // 3. MatchId List 가져오기
        List<MatchDto> matchDtoList = new ArrayList<>();
        Optional<SummonerMatch> summonerMatch = summonerMatchRepository.findOneByPuuid(summoner.getPuuid());

        // 4-1. Match List 가져오기
        if (summonerMatch.isPresent()) {
            List<String> matchIds = summonerMatch.get().getSummonerMatchIds().stream()
                // 특정 아이디 값이 최초로 작은 시점을 찾기
                .filter(matchId -> matchId.compareTo(lastMatchId) < 0)
                .limit(20).toList();

            matchDtoList = getMatchDtoList(matchIds);
        }

        return matchDtoList;
    }

    private Summoner getSummoner(String summonerName) {
        String internalName = keywordToInternalName(summonerName);
        Summoner summoner = summonerRepository.findOneByInternalName(internalName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "소환사 검색 결과가 존재하지 않습니다."));
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
}
