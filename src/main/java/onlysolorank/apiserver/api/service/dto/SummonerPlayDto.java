package onlysolorank.apiserver.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.summoner_play.BaseSummonerPlay;
import onlysolorank.apiserver.domain.summoner_play.SummonerPlay;

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
@Builder
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
    @Schema(description = "평균 데스가 0인 경우 true, 이 경우 avgKda가 null로 표기")
    private boolean isPerfect;
    private Long championId;
    private String championName;
    private Double avgGold;
    private Double avgDamage;
    private Integer maxKill;
    private Integer maxDeath;

    public static SummonerPlayDto from(BaseSummonerPlay summonerPlay) {
        boolean isPerfect = false;
        Double avgKda = summonerPlay.getAvgKda();
        Integer totalDeath = summonerPlay.getTotalDeath();

        if (totalDeath.equals(0)) {
            isPerfect = true;
            avgKda = null;
        }

        return SummonerPlayDto.builder()
            .totalPlays(summonerPlay.getTotalPlays())
            .avgCsPerMinute(summonerPlay.getAvgCsPerMinute())
            .avgCs(summonerPlay.getAvgCs())
            .avgKda(summonerPlay.getAvgKda())
            .avgKill(summonerPlay.getAvgKill())
            .avgDeath(summonerPlay.getAvgDeath())
            .avgAssist(summonerPlay.getAvgAssist())
            .winRate(summonerPlay.getWinRate())
            .totalWin(summonerPlay.getTotalWin())
            .totalDefeat(summonerPlay.getTotalDefeat())
            .championId(summonerPlay.getChampionId())
            .championName(summonerPlay.getChampionName())
            .isPerfect(isPerfect)
            .avgKda(avgKda)
            .avgGold(summonerPlay.getAvgGold())
            .avgDamage(summonerPlay.getAvgDamage())
            .maxKill(summonerPlay.getMaxKill())
            .maxDeath(summonerPlay.getMaxDeath())
            .build();
    }

}
