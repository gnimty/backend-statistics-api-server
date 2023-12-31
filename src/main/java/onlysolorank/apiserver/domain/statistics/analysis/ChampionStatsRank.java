package onlysolorank.apiserver.domain.statistics.analysis;

import lombok.Getter;
import onlysolorank.apiserver.domain.dto.Lane;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis
 * fileName       : ChampionStatisticsRank
 * author         : solmin
 * date           : 1/3/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/3/24        solmin       최초 생성
 */

@Document
@Getter
public class ChampionStatsRank extends BaseChampionStat {

    @Field("teamPosition")
    private Lane position;

    @Field("ban_rate")
    private Double banRate;

    public ChampionStatsRank(String tier, Long championId, Double winRate, Double pickRate, Long plays,
        Double score, Lane position, Double banRate) {
        super(tier, championId, winRate, pickRate, plays, score);
        this.position = position;
        this.banRate = banRate;

    }
}
