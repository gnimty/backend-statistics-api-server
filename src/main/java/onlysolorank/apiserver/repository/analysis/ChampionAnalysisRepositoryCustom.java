package onlysolorank.apiserver.repository.analysis;

import java.util.List;
import java.util.Optional;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
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
    Optional<ChampionAnalysis> findTop1ByChampionIdAndPositionAndTier(QueueType queueType, Long championId, Lane position, Tier upperTier);

    Optional<ChampionAnalysis> findTop1ByChampionIdAndTier(QueueType queueType, Long championId, Tier upperTier);

    List<ChampionStatsRank> findChampionTierList(QueueType queueType, Lane position, Boolean brief, Tier upperTier);

    List<BaseChampionStat> findChampionAramTierList(QueueType queueType, Boolean brief, Tier upperTier);

    List<ChampionStatsRank> findChampionLaneSelectRate(Long championId, Tier tier);
}
