package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatistics;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : ChampionStatsDto
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class ChampionStatsDto {

    private Long championId;
    private String championName;
    private Integer totalPlays;
    private Double banRate;
    private Double avgCs;
    private Double avgKda;
    private Double avgKill;
    private Double avgDeath;
    private Double avgAssist;
    private Double winRate;
    private Integer totalWin;
    private Integer totalDefeat;

    @Builder
    public ChampionStatsDto(ChampionStatistics championStatistics) {
        this.championId = championStatistics.getChampionId();
        this.championName = championStatistics.getChampionName();
        this.totalPlays = championStatistics.getTotalPlays();
        this.banRate = championStatistics.getBanRate();
        this.avgCs = championStatistics.getAvgCs();
        this.avgKda = championStatistics.getAvgKda();
        this.avgKill = championStatistics.getAvgKill();
        this.avgDeath = championStatistics.getAvgDeath();
        this.avgAssist = championStatistics.getAvgAssist();
        this.winRate = championStatistics.getWinRate();
        this.totalWin = championStatistics.getTotalWin();
        this.totalDefeat = championStatistics.getTotalDefeat();
    }
}
