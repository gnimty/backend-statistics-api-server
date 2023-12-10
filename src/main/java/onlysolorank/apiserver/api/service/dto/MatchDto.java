package onlysolorank.apiserver.api.service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.dto.Tier;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : MatchDto
 * author         : solmin
 * date           : 11/7/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/7/23        solmin       최초 생성
 */

@Data
@Builder
public class MatchDto {

    private String matchId;
    private String version;
    private LocalDateTime gameStartAt;
    private LocalDateTime gameEndAt;
    private Long gameDuration;
    private Integer queueId;
    private Boolean earlyEnded;
    private Tier averageTier;

    public static MatchDto from(Match match) {
        if (match.getAverageTier()!=null) {
            return MatchDto.builder()
                .matchId(match.getMatchId())
                .version(match.getVersion())
                .gameStartAt(match.getGameStartAt())
                .gameEndAt(match.getGameEndAt())
                .gameDuration(match.getGameDuration())
                .queueId(match.getQueueId())
                .earlyEnded(match.getEarlyEnded())
                .averageTier(match.getAverageTier())
                .build();
        }
        return MatchDto.builder()
            .matchId(match.getMatchId())
            .version(match.getVersion())
            .gameStartAt(match.getGameStartAt())
            .gameEndAt(match.getGameEndAt())
            .gameDuration(match.getGameDuration())
            .queueId(match.getQueueId())
            .earlyEnded(match.getEarlyEnded())
            .build();

    }
}
