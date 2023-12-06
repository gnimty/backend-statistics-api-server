package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.History;
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
    private ZonedDateTime updatedAt;

    public static SummonerTierDto from(Summoner summoner, QueueType queueType) {
        if (queueType==QueueType.RANK_SOLO){
            if (Objects.isNull(summoner.getQueue())){
                return null;
            }
            return SummonerTierDto.builder()
                .tier(Tier.valueOf(summoner.getQueue()))
                .division(summoner.getTier())
                .lp(summoner.getLp())
                .build();
        } else if (queueType==QueueType.RANK_FLEX) {
            if (Objects.isNull(summoner.getQueueFlex())){
                return null;
            }
            return SummonerTierDto.builder()
                .tier(Tier.valueOf(summoner.getQueueFlex()))
                .division(summoner.getTier())
                .lp(summoner.getLp())
                .build();
        }
        return null;
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