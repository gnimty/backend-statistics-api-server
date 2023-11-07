package onlysolorank.apiserver.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.MatchDto;
import onlysolorank.apiserver.api.service.dto.ParticipantBriefDto;
import onlysolorank.apiserver.api.service.dto.ParticipantDto;
import onlysolorank.apiserver.api.service.dto.QueueDto;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Participant;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : MatchBriefDto
 * author         : solmin
 * date           : 10/11/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/11/23        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class MatchBriefRes {

    private String matchId;
    private String gameVersion;
    private LocalDateTime gameEndAt;
    private Long gameDuration;
    private Boolean gameEndedInEarlySurrender;
    private QueueDto queueInfo;
    private Boolean win;
    private ParticipantDto participant;
    private List<ParticipantBriefDto> allParticipants;

    @Builder
    public MatchBriefRes(MatchDto match, ParticipantDto participant,
                         List<ParticipantBriefDto> allParticipants) {
        this.matchId = match.getMatchId();
        this.gameVersion = match.getVersion();
        this.gameEndAt = match.getGameEndAt();
        this.queueInfo = QueueDto.builder().queueId(match.getQueueId()).build();
        this.gameEndedInEarlySurrender = match.getEarlyEnded();
        this.gameDuration = match.getGameDuration();
        this.participant = participant;
        this.win = participant.getWin();
        this.allParticipants = allParticipants;
    }
}
