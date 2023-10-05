package onlysolorank.apiserver.domain.statistics.analysis.counter;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis.counterpart
 * fileName       : BaseCounter
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@Document
@Getter
public class BaseCounter {
    @Id
    private String id;

    private Double score;
    @Field("win_rate")
    private Double winRate;

    @Field("pick_rate")
    private Double pickRate;

    @Field("pick")
    private Long plays;

    protected Long championId;
    protected Long opponentChampionId;
}
