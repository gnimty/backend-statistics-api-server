package onlysolorank.apiserver.api.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.dto.MatchBriefDto;
import onlysolorank.apiserver.api.service.dto.ParticipantBriefDto;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.Team;
import onlysolorank.apiserver.repository.match.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : MatchService
 * author         : solmin
 * date           : 2023/08/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/14        solmin       최초 생성
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {
    private final MatchRepository matchRepository;


    public List<Match> getMatchListByMatchIdList(List<String> matchIds){
        return matchRepository.findMatchesByMatchIdIn(matchIds);
    }

    public Optional<Match> getMatchById(String matchId){
        return matchRepository.findMatchByMatchId(matchId);
    }


}
