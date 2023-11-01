package onlysolorank.apiserver.repository.analysis;

import java.util.Optional;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
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
public interface ChampionAnalysisRepository extends MongoRepository<ChampionAnalysis, String> {

    Optional<ChampionAnalysis> findTop1ByChampionIdAndPositionAndTierOrderByVersionDesc(
        Long championId, PositionFilter position, TierFilter tier);

    Optional<ChampionAnalysis> findTop1ByChampionIdAndTierOrderByVersionDescPickRateDesc(
        Long championId, TierFilter tier);
}
