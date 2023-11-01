package onlysolorank.apiserver.domain.statistics.stat;

import lombok.Getter;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import org.springframework.data.annotation.Id;
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
public class BaseChampionStat {

    @Id
    private String id;

    private Long championId;

    @Field("teamPosition")
    private PositionFilter position;

    private TierFilter tier;
    @Field("win_cnt")
    private Long wins;

    @Field("pick_cnt")
    private Long plays;

    @Field("ban_cnt")
    private Long bans;


}
