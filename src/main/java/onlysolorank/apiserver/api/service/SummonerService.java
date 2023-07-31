package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.SummonerMatchResponseDto;
import onlysolorank.apiserver.api.dto.MatchDto;
import onlysolorank.apiserver.api.dto.SummonerDto;
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

import static onlysolorank.apiserver.utils.NameToInternalNameConverter.keywordToInternalName;

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
    public List<SummonerDto> getSummonerByInternalName(String internalName){
        // internal name으로 찾기
        List<SummonerDto> result = summonerRepository.findTop5ByInternalNameStartsWithOrderByInternalName(internalName)
            .stream().map(summoner -> SummonerDto.builder().summoner(summoner).build())
            .toList();

        return result;
    }

    public SummonerMatchResponseDto getSummonerMatchInfoBySummonerName(String summonerName) {

        // SummonerDto, DateTime, ChampionPlaysBriefDto, MatchDto

        String internalName = keywordToInternalName(summonerName);
        Summoner summoner = summonerRepository.findOneByInternalName(internalName)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND, "소환사 검색 결과가 존재하지 않습니다."));

        // 1. SummonerDto 생성
        SummonerDto summonerDto = SummonerDto.builder()
            .summoner(summoner)
            .build();

        // 2. datetime 가져오기 : updated 시점으로부터 2분 이후의 시간을 리턴
        LocalDateTime renewableAt = summoner.getUpdatedAt().plus(2, ChronoUnit.MINUTES);;

        // 3. MatchId List 가져오기
        SummonerMatch summonerMatch = summonerMatchRepository.findOneByPuuid(summoner.getPuuid())
            .orElseGet(() -> null);

        // 4-1. Match List 가져오기
        List<String> matchIds = summonerMatch.getSummonerMatchIds().stream()
            .limit(20).toList();

        List<Match> matches = matchRepository.findByMatchIdInCustom(matchIds);

        // 4-2. Participant와 이에 대응되는 Summoner List 가져오기
        List<Participant> participants = participantRepository.findByMatchIdInCustom(matchIds);

        // 중복 제거 후 가져오기
        List<String> puuids = participants.stream().map(p->p.getPuuid()).collect(Collectors.toSet()).stream().toList();
        summonerRepository.findSummonersByPuuidInCustom(puuids);

        List<MatchDto> matchDtoList = matches
            .stream()
            .map(match -> {
                List<Participant> participants = participantRepository.findByMatchId(match.getMatchId());
                List<Team> teams = teamRepository.findByMatchId(match.getMatchId());

                return MatchDto.builder().match(match).participants(participants).teams(teams).build();
            }).toList();

        return new SummonerMatchResponseDto(summonerDto, renewableAt, matchDtoList);
    }
}
