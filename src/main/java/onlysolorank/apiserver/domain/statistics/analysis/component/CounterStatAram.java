package onlysolorank.apiserver.domain.statistics.analysis.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Getter
public class CounterStatAram extends BaseComponentStat {

    @Field("c_championId")
    private Long championId;
}
