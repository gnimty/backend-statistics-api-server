package onlysolorank.apiserver.repository.counter;

import java.util.List;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.domain.statistics.analysis.counter.BaseCounter;

/**
 * packageName    : onlysolorank.apiserver.repository.counter
 * fileName       : ChampionCounterRepositoryCustom
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */
public interface ChampionCounterRepositoryCustom {

    List<BaseCounter> findCounterChampions(Long championId, PositionFilter position,
        boolean isCounter);
}
