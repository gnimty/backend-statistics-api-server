package onlysolorank.apiserver.repository.match;

import java.util.List;
import java.util.Optional;
import onlysolorank.apiserver.domain.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

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
 * 2023/09/04        solmin       Custom Repository 분리
 */
public interface MatchRepository extends MongoRepository<Match, String>, MatchRepositoryCustom {

    Optional<Match> findMatchByMatchId(String matchId);

    List<Match> findMatchesByMatchIdIn(List<String> matchIds);
}
