package onlysolorank.apiserver.repository.crawl;

import java.util.Optional;
import onlysolorank.apiserver.domain.Version;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * packageName    : onlysolorank.apiserver.repository.crawl
 * fileName       : VersionRepository
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */
public interface VersionRepository extends MongoRepository<Version, String> {

    Optional<Version> findOneByOrder(int order);
}
