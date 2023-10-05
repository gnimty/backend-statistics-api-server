package onlysolorank.apiserver.domain.statistics.analysis.counter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis.counter
 * fileName       : UtilityCounter
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@Document("counter_SUP_SUP")
@AllArgsConstructor
@Getter
public class UtilityCounter extends BaseCounter{
    @Field("SUP")
    protected Long championId;

    @Field("OPP_SUP")
    protected Long opponentChampionId;
}
