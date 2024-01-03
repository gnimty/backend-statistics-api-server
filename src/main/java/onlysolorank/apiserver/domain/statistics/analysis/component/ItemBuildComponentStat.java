package onlysolorank.apiserver.domain.statistics.analysis.component;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : onlysolorank.apiserver.domain.dto.component
 * fileName       : ItemRarityComponentStat
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@NoArgsConstructor
@Getter
public class ItemBuildComponentStat extends BaseComponentStat {

    private List<Long> itemBuild;
}
