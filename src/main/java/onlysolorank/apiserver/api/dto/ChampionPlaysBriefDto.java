package onlysolorank.apiserver.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import static onlysolorank.apiserver.utils.CustomConverter.doubleValueToHalfUp;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : ChampionPlaysBriefDto
 * author         : solmin
 * date           : 2023/08/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/09        solmin       최초 생성
 */

@Data
@ToString
public class ChampionPlaysBriefDto {
    private String championId;
    private String championName;
    private Double avgCs;
    private Integer totalPlays;
    private Double avgCsPerMinute;
    private Double avgKda;
    private Double avgKill;
    private Double avgDeath;
    private Double avgAssist;
    private Double winRate;
    private Integer totalWin;
    private Integer totalDefeat;

    @JsonIgnore
    private Integer totalCs;
    @JsonIgnore
    private Integer totalGameDuration;

    public ChampionPlaysBriefDto(String championId, String championName, Double avgCs, Integer totalPlays, Double avgCsPerMinute, Double avgKda, Double avgKill, Double avgDeath, Double avgAssist, Double winRate, Integer totalWin, Integer totalDefeat, Integer totalCs, Integer totalGameDuration) {
        this.championId = championId;
        this.championName = championName;
        this.avgCs = avgCs;
        this.totalPlays = totalPlays;
        this.avgCsPerMinute = avgCsPerMinute;
        this.avgKda = avgKda;
        this.avgKill = avgKill;
        this.avgDeath = avgDeath;
        this.avgAssist = avgAssist;
        this.winRate = winRate;
        this.totalWin = totalWin;
        this.totalDefeat = totalDefeat;
        this.totalCs = totalCs;
        this.totalGameDuration = totalGameDuration;
        this.avgCsPerMinute = doubleValueToHalfUp(totalCs.doubleValue() / (totalGameDuration.doubleValue()/60) , 2);
    }

}
