package onlysolorank.apiserver.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.MatchDto;
import onlysolorank.apiserver.api.service.dto.ParticipantDto;
import onlysolorank.apiserver.api.service.dto.QueueDto;
import onlysolorank.apiserver.api.service.dto.TeamDto;
import onlysolorank.apiserver.domain.Match;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : MatchDto
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class MatchDetailRes {

    private String matchId;
    private String gameVersion;
    private LocalDateTime gameEndAt;
    private QueueDto queueInfo;
    private Boolean gameEndedInEarlySurrender;
    private Long gameDuration;
    private List<ParticipantDto> participants;
    private List<TeamDto> teams;

    @Builder
    public MatchDetailRes(MatchDto match, List<ParticipantDto> participants, List<TeamDto> teams) {
        this.matchId = match.getMatchId();
        this.gameVersion = match.getVersion();
        this.gameEndAt = match.getGameEndAt();
        this.queueInfo = QueueDto.builder().queueId(match.getQueueId()).build();
        this.gameEndedInEarlySurrender = match.getEarlyEnded();
        this.gameDuration = match.getGameDuration();
        this.participants = participants;
        this.teams = teams;
    }

}
