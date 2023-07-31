package onlysolorank.apiserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
public class MatchDto {

    private String matchId;
    private String gameVersion;
    private LocalDateTime gameEndAt;
    private QueueDto queueInfo;
    private Boolean gameEndedInEarlySurrender;
    private Long gameDuration;
    private List<ParticipantDto> participants;
    private List<TeamDto> teams;

    @Builder
    public MatchDto(Match match, Map<Summoner, Participant> summonerParticipantPair, List<Team> teams) {
        this.matchId = match.getMatchId();
        this.gameVersion = match.getVersion();
        this.gameEndAt = match.getGameEndAt();
        this.queueInfo = QueueDto.builder().queueId(match.getQueueId()).build();
        this.gameEndedInEarlySurrender = match.getEarlyEnded();
        this.gameDuration = match.getGameDuration();
        this.participants = summonerParticipantPair.entrySet().stream()
            .map(sp -> ParticipantDto.builder().summoner(sp.getKey()).participant(sp.getValue()).build()).toList();
        this.teams = teams.stream().map(TeamDto::new).toList();
    }
}
