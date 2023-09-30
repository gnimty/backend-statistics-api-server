package onlysolorank.apiserver.repository.statistics;

import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.domain.statistics.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.tier.BaseChampionTier;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.statistics
 * fileName       : ChampionStatisticsRepositoryCustom
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */
public interface ChampionStatisticsRepositoryCustom {
    List<? extends BaseChampionStat> findStats(Period period, PositionFilter position, TierFilter tier);

    List<? extends BaseChampionTier> findTierStats(PositionFilter position);

}