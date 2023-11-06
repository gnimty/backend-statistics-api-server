package onlysolorank.apiserver.api.service.dto;

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
public class ChampionStatDto {

    private Long championId;
    private String championName;
    private Long plays;
    private Long wins;
    private Long defeats;
    private Long bans;

    @Builder
    public ChampionStatDto(BaseChampionStat stat, String championName) {
        this.championId = stat.getChampionId();
        this.championName = championName;
        this.plays = stat.getPlays();
        this.wins = stat.getWins();
        this.defeats = plays - wins;
        this.bans = stat.getBans();
    }

}
