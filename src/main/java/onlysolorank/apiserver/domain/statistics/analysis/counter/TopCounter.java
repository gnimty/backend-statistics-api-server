package onlysolorank.apiserver.domain.statistics.analysis.counter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis.counter
 * fileName       : TopCounter
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@Document("counter_TOP_TOP")
@AllArgsConstructor
@Getter
public class TopCounter extends BaseCounter{
    @Field("TOP")
    protected Long championId;

    @Field("OPP_TOP")
    protected Long opponentChampionId;
}
