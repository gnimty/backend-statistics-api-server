package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.statistics.stat.BaseChampionStat;

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
@AllArgsConstructor
public class ChampionStatBriefDto {

    private Long championId;
    private Long plays;
    private Long wins;
    private Long defeats;
    private Long bans;

    @Builder
    public ChampionStatBriefDto(BaseChampionStat stats) {
        this.championId = stats.getChampionId();
        this.plays = stats.getPlays();
        this.wins = stats.getWins();
        this.defeats = plays - wins;
        this.bans = stats.getBans();
    }

}
