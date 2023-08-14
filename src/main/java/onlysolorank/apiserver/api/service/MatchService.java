package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Team;
import onlysolorank.apiserver.repository.MatchRepository;
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
    private final TeamService teamService;
    private final ParticipantService participantService;


    public List<Match> getMatchListByMatchIdList(List<String> matchIds){
        return matchRepository.findByMatchIdInCustom(matchIds);
    }

    public Map<String, List<Team>> getTeamMapMappedByMatchId(List<String> matchIds){
        Map<String, List<Team>> teamMap = teamService.getTeamListByMatchIdList(matchIds).stream()
            .collect(Collectors.groupingBy(Team::getMatchId));

        return teamMap;
    }


}
