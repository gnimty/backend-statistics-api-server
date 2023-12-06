package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.doubleValueToHalfUp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : ChampionPlaysDto
 * author         : solmin
 * date           : 2023/08/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/09        solmin       최초 생성
 * 2023/08/10        solmin       totalDeath=0인 경우의 예외 처리를 위한 @JsonIgnore 필드 추가
 * 2023/08/28        solmin       필드 추가
 * 2023/08/30        solmin       DTO rename
 * 2023/09/04        solmin       DTO 철거 예정
 */

@Data
@ToString
public class ChampionPlayDto {

    private Integer totalPlays;
    private Double avgCsPerMinute;
    private Double avgKda;
    private Double avgKill;
    private Double avgDeath;
    private Double avgAssist;
    private Double winRate;
    private Integer totalWin;
    private Integer totalDefeat;
    private boolean isPerfect = false;

    @JsonIgnore
    private Integer championId;
    @JsonIgnore
    private String championName;
    @JsonIgnore
    private String puuid;
    @JsonIgnore
    private Integer totalCs;
    @JsonIgnore
    private Integer totalGameDuration;
    @JsonIgnore
    private Integer totalKill;
    @JsonIgnore
    private Integer totalDeath;
    @JsonIgnore
    private Integer totalAssist;

    @Builder
    public ChampionPlayDto(String puuid, Integer championId, String championName,
        Integer totalPlays, Double avgCsPerMinute, Double avgKill, Double avgDeath,
        Double avgAssist, Double winRate, Integer totalWin, Integer totalDefeat, Integer totalCs,
        Integer totalGameDuration,
        Integer totalKill, Integer totalDeath, Integer totalAssist) {
        this.puuid = puuid;
        this.championId = championId;
        this.championName = championName;
        this.totalPlays = totalPlays;
        this.avgCsPerMinute = avgCsPerMinute;
        this.totalKill = totalKill;
        this.totalDeath = totalDeath;
        this.totalAssist = totalAssist;
        // totalDeath == 0인 경우 Perfect 처리
        if (totalDeath != 0) {
            this.avgKda = doubleValueToHalfUp(
                (totalKill.doubleValue() + totalAssist.doubleValue()) / totalDeath.doubleValue(),
                3);
        } else {
            this.isPerfect = true;
        }
        this.avgKill = avgKill;
        this.avgDeath = avgDeath;
        this.avgAssist = avgAssist;
        this.winRate = winRate;
        this.totalWin = totalWin;
        this.totalDefeat = totalDefeat;
        this.totalCs = totalCs;
        this.totalGameDuration = totalGameDuration;
        this.avgCsPerMinute = doubleValueToHalfUp(
            totalCs.doubleValue() / (totalGameDuration.doubleValue() / 60), 2);
    }

}
