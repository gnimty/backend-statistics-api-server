package onlysolorank.apiserver.repository.team;

import onlysolorank.apiserver.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : TeamRepository
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 * 2023/09/04        solmin       Custom Repository 분리
 */
public interface TeamRepository extends MongoRepository<Team, String>, TeamRepositoryCustom {

    List<Team> findByMatchId(String matchId);

    List<Team> findByMatchIdIn(List<String> matchIds);
}
