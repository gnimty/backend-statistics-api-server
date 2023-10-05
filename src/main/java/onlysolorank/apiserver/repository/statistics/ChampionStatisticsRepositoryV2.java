package onlysolorank.apiserver.repository.statistics;

import onlysolorank.apiserver.domain.statistics.stat.BaseChampionStat;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * packageName    : onlysolorank.apiserver.repository.statistics
 * fileName       : ChampionStatisticsRepository_v2
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */
public interface ChampionStatisticsRepositoryV2 extends MongoRepository<BaseChampionStat, String>{

}
