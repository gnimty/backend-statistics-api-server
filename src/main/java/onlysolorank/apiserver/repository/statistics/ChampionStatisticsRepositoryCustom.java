package onlysolorank.apiserver.repository.statistics;

import java.util.List;
import java.util.Optional;

import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.domain.dto.Position;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.domain.statistics.stat.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.tier.BaseChampionTier;

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

    List<? extends BaseChampionStat> findStats(Period period, Position position,
        Tier tier);

    List<? extends BaseChampionTier> findTier(Position position, Boolean brief);

    Optional<? extends BaseChampionTier> findTier(Position position, Long championId);
}
