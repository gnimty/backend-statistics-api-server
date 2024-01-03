package onlysolorank.apiserver.repository.analysis;

import java.util.List;
import java.util.Optional;
import onlysolorank.apiserver.domain.dto.Position;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.statistics.analysis.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionStatsRank;

/**
 * packageName    : onlysolorank.apiserver.repository.analysis
 * fileName       : ChampionAnalysisRepositoryCustom
 * author         : solmin
 * date           : 1/3/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/3/24        solmin       최초 생성
 */
public interface ChampionAnalysisRepositoryCustom {
    Optional<ChampionAnalysis> findTop1ByChampionIdAndPositionAndTier(QueueType queueType, Long championId, Position position, String upperTier);

    Optional<ChampionAnalysis> findTop1ByChampionIdAndTier(QueueType queueType, Long championId, String upperTier);

    List<ChampionStatsRank> findChampionTierList(QueueType queueType, Position position, Boolean brief, String upperTier);

    List<BaseChampionStat> findChampionAramTierList(QueueType queueType, Boolean brief, String upperTier);
}
