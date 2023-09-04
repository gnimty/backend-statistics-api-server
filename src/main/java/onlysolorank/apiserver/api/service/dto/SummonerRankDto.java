package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;

import java.util.List;

import static onlysolorank.apiserver.utils.CustomFunctions.doubleValueToHalfUp;

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
    private List<Integer> mostPlayedChampionIds;
    private Integer totalWin;
    private Integer totalDefeat;
    private Double winRate;
    private Integer rank;

    @Builder
    public SummonerRankDto(Summoner summoner,List<Integer> championIds, Integer rank){
        this.summoner = SummonerDto.builder().summoner(summoner).build();
        this.totalWin = summoner.getTotalWin();
        this.totalDefeat = summoner.getTotalDefeat();
        this.winRate = doubleValueToHalfUp(totalWin.doubleValue()/totalDefeat.doubleValue(), 3);
        this.mostPlayedChampionIds = championIds;
        this.rank = rank;
    }
}
