package onlysolorank.apiserver.domain.statistics.analysis.counter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis.counter
 * fileName       : JungleCounter
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@Document("counter_JUG_JUG")
@AllArgsConstructor
@Getter
public class JungleCounter extends BaseCounter {

    @Field("JUG")
    protected Long championId;

    @Field("OPP_JUG")
    protected Long opponentChampionId;
}
