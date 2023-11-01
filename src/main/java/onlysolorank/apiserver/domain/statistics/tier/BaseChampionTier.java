package onlysolorank.apiserver.domain.statistics.tier;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.tier
 * fileName       : BaseTierStatistics
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Document
@Getter
public class BaseChampionTier {

    protected Long championId;
    @Id
    private String id;
    @Field("adjust_win_rate")
    private Double adjustWinRate;

    @Field("win_rate")
    private Double winRate;

    @Field("pick_rate")
    private Double pickRate;
    @Field("ban_rate")
    private Double banRate;
    @Field("pick")
    private Long plays;
    private Double score;
    private ChampionTier tier;

}
