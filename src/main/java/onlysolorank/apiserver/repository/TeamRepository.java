package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
 */
public interface TeamRepository extends MongoRepository<Team, String> {

    List<Team> findByMatchId(String matchId);

    @Query("{'matchId': {$in: ?0}}")
    List<Team> findByMatchIdInCustom(List<String> matchIds);
}
