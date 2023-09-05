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
    private SummonerPlayDto summonerPlayDto;
    private Long spellDId;
    private Long spellFId;
    private Perk perks;

    @Builder
    public IngameParticipantDto(SpectatorV4GetCurrentGameInfo.CurrentGameParticipant participant, SummonerDto summoner, SummonerPlayDto summonerPlayDto){
        this.teamId = participant.getTeamId();
        this.summoner = summoner;
        this.championId = participant.getChampionId();
        // TODO 추후 추가 예정
        this.championName = null;
        this.summonerPlayDto = summonerPlayDto;
        this.spellDId = participant.getSpell1Id();
        this.spellFId = participant.getSpell2Id();
        this.perks = Perk.getPerk(participant.getPerks());
    }
}
