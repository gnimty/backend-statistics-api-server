package onlysolorank.apiserver.domain.statistics.analysis.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.dto
 * fileName       : BaseComponentStat
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
public class BaseComponentStat {
    @Field("win_rate")
    private Double winRate;

    @Field("select_ratio")
    private Double pickRate;

    @Field("total")
    private Long plays;
}
