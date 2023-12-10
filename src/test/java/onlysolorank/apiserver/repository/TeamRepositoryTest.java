package onlysolorank.apiserver.repository;

import java.util.List;
import onlysolorank.apiserver.domain.Team;
import onlysolorank.apiserver.repository.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : TeamRepositoryTest
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 */
@ActiveProfiles("test")
@SpringBootTest
class TeamRepositoryTest {

    private final String matchId = "KR_6574632344";
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void getParticipantsInMatch() {
        List<Team> results = teamRepository.findByMatchId(matchId);

        for (Team result : results) {
            System.out.println("result = " + result);
        }
    }
}