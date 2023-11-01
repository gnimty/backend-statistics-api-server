package onlysolorank.apiserver.domain.statistics.analysis.counter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis.counter
 * fileName       : BottomCounter
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@Document("counter_ADC_ADC")
@AllArgsConstructor
@Getter
public class BottomCounter extends BaseCounter {

    @Field("ADC")
    protected Long championId;

    @Field("OPP_ADC")
    protected Long opponentChampionId;
}
