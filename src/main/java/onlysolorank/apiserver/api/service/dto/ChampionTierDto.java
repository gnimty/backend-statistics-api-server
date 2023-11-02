package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
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
public class ChampionTierDto {

    //    private Double adjustWinRate;
    private Long championId;
    private String championName;
    private Double winRate;
    private Double pickRate;
    private Double banRate;
    private Long plays;
    private String tier;

    @Builder
    public ChampionTierDto(BaseChampionTier stat, String championName) {
        this.winRate = stat.getWinRate();
        this.pickRate = stat.getPickRate();
        this.banRate = stat.getBanRate();
        this.championName = championName;
        this.plays = stat.getPlays();
        this.tier = stat.getTier().getValue();
        this.championId = stat.getChampionId();
    }
}
