package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatsRank;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : ChampionTierStatDto
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Data
@Builder
public class ChampionTierDto {

    //    private Double adjustWinRate;
    private Long championId;
    private String championName;
    private Double winRate;
    private Double pickRate;
    @JsonInclude(Include.NON_NULL)
    private Double banRate;
    private Long plays;
    private String tier;
    private Double score;
    private List<CounterStat> counters;

    public static ChampionTierDto fromRankTier(ChampionStatsRank stat, String championName) {
        return ChampionTierDto.builder()
            .championName(championName)
            .winRate(stat.getWinRate())
            .pickRate(stat.getPickRate())
            .banRate(stat.getBanRate())
            .plays(stat.getPlays())
            .tier(stat.getTier())
            .counters(stat.getCounters())
            .championId(stat.getChampionId())
            .score(stat.getScore())
            .build();
    }
}
