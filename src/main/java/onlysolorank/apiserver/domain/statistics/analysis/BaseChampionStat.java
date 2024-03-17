package onlysolorank.apiserver.domain.statistics.analysis;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics
 * fileName       : BaseStatisticsEntity
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Document
@Getter
@AllArgsConstructor
public class BaseChampionStat {

    private String tier;

    private Long championId;
//    @Transient
//    private String championName;

    @Field("win_rate")
    private Double winRate;

    @Field("pick_rate")
    private Double pickRate;

    @Field("pick_cnt")
    private Long plays;

    private Double score;

    @Field("counter")
    private List<CounterStat> counters;
}
