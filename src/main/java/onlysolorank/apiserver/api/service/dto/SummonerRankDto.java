package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.doubleValueToHalfUp;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.Position;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SummonerRankDto
 * author         : solmin
 * date           : 2023/08/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/15        solmin       최초 생성
 */

@Data
public class SummonerRankDto {

    private SummonerDto summoner;
    private List<Long> mostPlayedChampionIds;
    private List<Position> mostLanes;
    private Integer totalWin;
    private Integer totalDefeat;
    private Double winRate;
    private Integer rank;

    @Builder
    public SummonerRankDto(Summoner summoner, Integer rank) {
        this.summoner = SummonerDto.from(summoner);
        this.totalWin = summoner.getTotalWin();
        this.totalDefeat = summoner.getTotalDefeat();
        this.winRate = doubleValueToHalfUp(totalWin.doubleValue() / (totalWin.doubleValue() + totalDefeat.doubleValue()), 3);
        this.mostPlayedChampionIds = summoner.getMostChampionIds();
        this.mostLanes = summoner.getMostLanes();
        this.rank = rank;
    }
}
