package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Summoner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : MatchRepository
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */
public interface MatchRepository extends MongoRepository<Match, String> {
    // For test
    Optional<Match> findOneByMatchId(String matchId);

    @Query("{'matchId': {$in: ?0}}")
    List<Match> findByMatchIdInCustom(List<String> matchIds);

}
