package onlysolorank.apiserver.repository.statistics;

import onlysolorank.apiserver.domain.statistics.stat.ChampionStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.repository.statistics
 * fileName       : ChampionStatisticsRepository
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 */

public interface ChampionStatisticsRepository extends MongoRepository<ChampionStatistics, String> {

    Optional<ChampionStatistics> findOneByChampionId(Long championId);

    List<ChampionStatistics> findAllByOrderByTotalPlaysDesc();
}
