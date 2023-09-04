package onlysolorank.apiserver.api.service.dto;

import lombok.Data;
import lombok.ToString;
import onlysolorank.apiserver.domain.SummonerPlay;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SummonerPlayDto
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 */

@Data
@ToString
public class SummonerPlayDto {
    private Integer totalPlays;
    private Double avgCs;
    private Double avgCsPerMinute;
    private Double avgKda;
    private Double avgKill;
    private Double avgDeath;
    private Double avgAssist;
    private Double winRate;
    private Integer totalWin;
    private Integer totalDefeat;
    private boolean isPerfect = false;
    private Integer championId;
    private String championName;

    public SummonerPlayDto(SummonerPlay summonerPlay) {
        this.totalPlays = summonerPlay.getTotalPlays();
        this.avgCsPerMinute = summonerPlay.getAvgCsPerMinute();
        this.avgKda = summonerPlay.getAvgKda();
        this.avgKill = summonerPlay.getAvgKill();
        this.avgDeath = summonerPlay.getAvgDeath();
        this.avgAssist = summonerPlay.getAvgAssist();
        this.winRate = summonerPlay.getWinRate();
        this.totalWin = summonerPlay.getTotalWin();
        this.totalDefeat = summonerPlay.getTotalDefeat();
        this.championId = summonerPlay.getChampionId();
        this.championName = summonerPlay.getChampionName();
        this.avgCs = summonerPlay.getAvgCs();
        if(summonerPlay.getTotalDeath() == 0){
            this.isPerfect = true;
            this.avgKda = null;
        }
    }

}
