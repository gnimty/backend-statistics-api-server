package onlysolorank.apiserver.domain.statistics.analysis.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.statistics.stat.BaseChampionStat;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis.component
 * fileName       : SynergyStat
 * author         : solmin
 * date           : 3/24/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/24/24        solmin       최초 생성
 */

@NoArgsConstructor
@Getter
public class SynergyStat extends BaseComponentStat {
    @Field("s_championId")
    private Long championId;

    @Field("s_teamPosition")
    private Lane lane;
}
