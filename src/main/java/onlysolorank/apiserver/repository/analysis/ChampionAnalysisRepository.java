package onlysolorank.apiserver.repository.analysis;

import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * packageName    : onlysolorank.apiserver.repository.analysis
 * fileName       : ChampionAnalysisRepository
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */
public interface ChampionAnalysisRepository extends MongoRepository<ChampionAnalysis, String>, ChampionAnalysisRepositoryCustom {

//    Optional<ChampionAnalysisRank> findTop1ByChampionIdAndPositionAndTierOrderByVersionDesc(
//        Long championId, Position position, String upperTier);
//
//    Optional<ChampionAnalysisRank> findTop1ByChampionIdAndTierOrderByVersionDescPickRateDesc(
//        Long championId, String upperTier);
}
