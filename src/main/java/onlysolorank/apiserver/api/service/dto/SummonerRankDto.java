package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

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
    private Integer totalWin = 0;
    private Integer totalDefeat = 0;
    private Double winRate;
    private Integer rank;

    @Builder
    public SummonerRankDto(Summoner summoner, Integer rank) {
        this.summoner = SummonerDto.from(summoner);
        if (summoner.getTotalWin() != null) {
            this.totalWin = summoner.getTotalWin();
        }
        if (summoner.getTotalDefeat() != null) {
            this.totalDefeat = summoner.getTotalDefeat();
        }
        this.winRate = divideAndReturnDouble(totalWin, (totalWin + totalDefeat), 3).get();
        this.mostPlayedChampionIds = summoner.getMostChampionIds();
        this.mostLanes = summoner.getMostLanes();
        this.rank = rank;
    }
}
