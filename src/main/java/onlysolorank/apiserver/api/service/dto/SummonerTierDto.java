package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.History;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;


/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : SoloTierDto
 * author         : solmin
 * date           : 2023/07/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/24        solmin       최초 생성
 * 2023/07/31        solmin       Getter 이름 일부 변경
 * 2023/08/16        solmin       getLeaguePoints to getLp (Summoner 도큐먼트 필드명 변경)
 */

@Data
@AllArgsConstructor
@Builder
public class SummonerTierDto {

    private Tier tier;
    private Integer division;
    private Integer lp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer plays;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer wins;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer defeats;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double winRate;
    private List<Long> mostChampionIds;
    private List<Lane> mostLanes;
    private Integer mmr;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ZonedDateTime updatedAt;

    public static SummonerTierDto from(Summoner summoner, QueueType queueType) {
        Integer wins;
        Integer defeats;

        Double winRate = null;
        Integer plays = null;

        if (queueType==QueueType.RANK_SOLO){
            if (Objects.isNull(summoner.getQueue())){
                return null;
            }

            wins = summoner.getTotalWin();
            defeats = summoner.getTotalDefeat();

            if (wins != null && defeats != null) {
                plays = wins + defeats;
                winRate = divideAndReturnDouble(wins, (wins + defeats), 3).get();
            }

            return SummonerTierDto.builder()
                .tier(Tier.valueOf(summoner.getQueue()))
                .division(summoner.getTier())
                .lp(summoner.getLp())
                .plays(plays)
                .wins(wins)
                .defeats(defeats)
                .winRate(winRate)
                .mostChampionIds(summoner.getMostChampionIds())
                .mostLanes(summoner.getMostLanes())
                .mmr(summoner.getMmr())
                .build();

        } else if (queueType==QueueType.RANK_FLEX) {
            if (Objects.isNull(summoner.getQueueFlex())){
                return null;
            }

            wins = summoner.getTotalWinFlex();
            defeats = summoner.getTotalDefeatFlex();

            if (wins != null && defeats != null) {
                plays = wins + defeats;
                winRate = divideAndReturnDouble(wins, (wins + defeats), 3).get();
            }

            return SummonerTierDto.builder()
                .tier(Tier.valueOf(summoner.getQueueFlex()))
                .division(summoner.getTierFlex())
                .lp(summoner.getLpFlex())
                .plays(plays)
                .wins(wins)
                .defeats(defeats)
                .winRate(winRate)
                .mostChampionIds(summoner.getMostChampionIdsFlex())
                .mostLanes(summoner.getMostLanesFlex())
                .mmr(summoner.getMmrFlex())
                .build();
        } else{
            return null;
        }




    }

    public static SummonerTierDto from(History history) {
        return SummonerTierDto.builder()
            .tier(Tier.valueOf(history.getQueue()))
            .division(history.getTier())
            .lp(history.getLeaguePoints())
            .updatedAt(history.getUpdatedAt())
            .build();
    }

}
