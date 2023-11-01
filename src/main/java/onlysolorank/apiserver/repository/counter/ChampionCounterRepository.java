package onlysolorank.apiserver.repository.counter;

import onlysolorank.apiserver.domain.statistics.analysis.counter.BaseCounter;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * packageName    : onlysolorank.apiserver.repository.counter
 * fileName       : ChampionCounterRepository
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */
public interface ChampionCounterRepository extends MongoRepository<BaseCounter, String>,
    ChampionCounterRepositoryCustom {

}
