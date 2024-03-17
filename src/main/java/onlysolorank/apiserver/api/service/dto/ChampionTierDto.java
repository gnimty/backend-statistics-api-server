package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.statistics.analysis.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionStatsRank;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;
import onlysolorank.apiserver.domain.statistics.tier.BaseChampionTier;

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
    private List<CounterStat> counters;


    public static ChampionTierDto fromBaseTier(BaseChampionStat stat, String championName) {
        return ChampionTierDto.builder()
            .championName(championName)
            .winRate(stat.getWinRate())
            .pickRate(stat.getPickRate())
            .plays(stat.getPlays())
            .tier(stat.getTier())
            .championId(stat.getChampionId())
            .counters(stat.getCounters())
            .build();
    }

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
            .build();
    }
}
