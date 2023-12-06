package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.statistics.stat.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatistics;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : ChampionStatsBriefDto
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Data
// 임시용, 추후 ChampionStatsDto로 변환할 예정
public class ChampionTotalStatDto {

    private Long championId;
    private String championName;
    private Long plays;
    private Long wins;
    private Long defeats;
    private Long bans;

    @Builder
    public ChampionTotalStatDto(BaseChampionStat stat, String championName) {
        this.championId = stat.getChampionId();
        this.championName = championName;
        this.plays = stat.getPlays();
        this.wins = stat.getWins();
        this.defeats = plays - wins;
        this.bans = stat.getBans();
    }

    @Data
    @AllArgsConstructor
    public static class ChampionStatsDto {

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

}