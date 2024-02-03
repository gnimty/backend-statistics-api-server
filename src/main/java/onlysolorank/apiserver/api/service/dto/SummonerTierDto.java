package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlysolorank.apiserver.domain.Participant;
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
@NoArgsConstructor
@Builder
public class SummonerTierDto {

    @Schema(example = "challenger", description = "소환사 티어 대분류")
    private Tier tier;

    @Schema(example = "1", description = "소환사 티어 소분류, 1~4")
    private Integer division;

    @Schema(example = "56", description = "랭크 포인트")
    private Integer lp;

    @Schema(example = "100", description = "랭크 게임 플레이 횟수")
    private Integer plays;

    @Schema(example = "78", description = "랭크 게임 승리 횟수")
    private Integer wins;

    @Schema(example = "22", description = "랭크 게임 패배 횟수")
    private Integer defeats;

    @Schema(example = "0.78", description = "랭크 게임 승률, 소수점 둘째 자리까지 표기")
    private Double winRate;

    @Schema(example = "[3,266,4]", description = "최근 20게임동안 자주 플레이한 챔피언 id")
    private List<Long> mostChampionIds = new ArrayList<>();

    @Schema(example = "[TOP, MIDDLE]", description = "최근 20게임동안 자주 플레이한 라인")
    private List<Lane> mostLanes = new ArrayList<>();;

    @Schema(example = "3821", description = "소환사 티어 환산 수치")
    private Integer mmr;

    public static SummonerTierDto from(Summoner summoner, QueueType queueType) {
        Integer wins;
        Integer defeats;

        Double winRate = null;
        Integer plays = null;

        List<Long> mostChampionIdsFlex = Optional.ofNullable(summoner.getMostChampionIdsFlex())
            .orElse(new ArrayList<>());

        List<Lane> mostLanes = Optional.ofNullable(summoner.getMostLanes()).orElse(new ArrayList<>());

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
                .mostChampionIds(mostChampionIdsFlex)
                .mostLanes(mostLanes)
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
                .mostChampionIds(mostChampionIdsFlex)
                .mostLanes(mostLanes)
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
//            .updatedAt(history.getUpdatedAt())
            .build();
    }

//    public static SummonerTierDto from(Participant participant) {
//        // queue, tier, leaguepoints가 하나라도 null이면 soloTier를 null로
//        if (participant.getQueue() == null || participant.getTier() == null || participant.getLeaguePoints() == null) {
//            return null;
//        }
//
//        return SummonerTierDto.builder()
//            .tier(Tier.valueOf(participant.getQueue()))
//            .division(Integer.parseInt(participant.getTier()))
//            .lp(Integer.parseInt(participant.getLeaguePoints()))
//            .build();
//    }

}
