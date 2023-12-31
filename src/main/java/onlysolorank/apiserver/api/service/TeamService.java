package onlysolorank.apiserver.api.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.domain.Team;
import onlysolorank.apiserver.repository.team.TeamRepository;
import org.springframework.stereotype.Service;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : TeamService
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
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getTeamListByMatchIdList(List<String> matchIds) {
        return teamRepository.findByMatchIdIn(matchIds);
    }

    public List<Team> getTeamListByMatchId(String matchId) {
        return teamRepository.findByMatchId(matchId);
    }

}

