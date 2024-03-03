package onlysolorank.apiserver.api.service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
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

@Getter
@Builder
public class MatchDto {

    private String matchId;
    private String version;
    private LocalDateTime gameStartAt;
    private LocalDateTime gameEndAt;
    private Long gameDuration;
    private QueueDto queueInfo;
    private Boolean earlyEnded;
    private Tier avgTier;
    private Integer avgDivision;

    public static MatchDto from(Match match) {
        if (match.getAvgTier()!=null) {
            return MatchDto.builder()
                .matchId(match.getMatchId())
                .version(match.getVersion())
                .gameStartAt(match.getGameStartAt())
                .gameEndAt(match.getGameEndAt())
                .gameDuration(match.getGameDuration())
                .queueInfo(QueueDto.builder().queueId(match.getQueueId()).build())
                .earlyEnded(match.getEarlyEnded())
                .avgTier(match.getAvgTier())
                .avgDivision(match.getAvgDivision())
                .build();
        }
        return MatchDto.builder()
            .matchId(match.getMatchId())
            .version(match.getVersion())
            .gameStartAt(match.getGameStartAt())
            .gameEndAt(match.getGameEndAt())
            .gameDuration(match.getGameDuration())
            .queueInfo(QueueDto.builder().queueId(match.getQueueId()).build())
            .earlyEnded(match.getEarlyEnded())
            .build();
    }
}
