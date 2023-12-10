package onlysolorank.apiserver.repository.season;

import java.util.Optional;
import onlysolorank.apiserver.domain.Season;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * packageName    : onlysolorank.apiserver.repository.season
 * fileName       : SeasonRepository
 * author         : solmin
 * date           : 12/10/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/10/23        solmin       최초 생성
 */
public interface SeasonRepository extends MongoRepository<Season, String> {
    Optional<Season> findTop1SeasonByOrderByStartAtDesc();
}
