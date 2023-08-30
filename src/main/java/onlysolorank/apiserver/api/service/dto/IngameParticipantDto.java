package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.dto.Perk;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : IngameParticipantDto
 * author         : solmin
 * date           : 2023/08/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/30        solmin       최초 생성
 */

@Data
public class IngameParticipantDto {
    private Long teamId;
    private SummonerDto summoner;
    private Long championId;
    private String championName;
    private ChampionPlaysDto championPlaysInfo;
    private Long spellDId;
    private Long spellFId;
    private Perk perks;

    @Builder
    public IngameParticipantDto(SpectatorV4GetCurrentGameInfo.CurrentGameParticipant participant, SummonerDto summoner, ChampionPlaysDto championPlaysDto){
        this.teamId = participant.getTeamId();
        this.summoner = summoner;
        this.championId = participant.getChampionId();
        this.championName = championPlaysDto.getChampionName();
        this.championPlaysInfo = championPlaysDto;
        this.spellDId = participant.getSpell1Id();
        this.spellFId = participant.getSpell2Id();
        this.perks = Perk.getPerk(participant.getPerks());
    }
}
