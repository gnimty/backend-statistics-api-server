package onlysolorank.apiserver.domain.statistics.stat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onlysolorank.apiserver.domain.statistics.analysis.component.BaseComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStatAram;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.stat
 * fileName       : AramStat
 * author         : solmin
 * date           : 3/24/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/24/24        solmin       최초 생성
 */

@Document("champion_statistics_aram")
@Getter
@AllArgsConstructor
public class AramStat {
    private Long championId;
    private String championName;
    @Field("win_rate")
    private Double winRate;
    @Field("pick_rate")
    private Double pickRate;

    @Field("pick_cnt")
    private Long plays;
    private String tier;

    @Field("counter")
    private List<CounterStatAram> counters;
    private Double score;


}

