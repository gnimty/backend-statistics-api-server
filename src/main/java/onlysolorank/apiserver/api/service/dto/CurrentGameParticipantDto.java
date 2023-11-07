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
 * 2023/09/13        solmin       챔피언 이름 주입
 */

@Data
public class CurrentGameParticipantDto {

    private Long teamId;
    private SummonerDto summoner;
    private Long championId;
    private String championName;
    private SummonerPlayDto summonerPlayDto;
    private Long spellDId;
    private Long spellFId;
    private Perk perks;

    @Builder
    public CurrentGameParticipantDto(SpectatorV4CurrentGameDto.CurrentGameParticipant participant,
                                     SummonerDto summoner, SummonerPlayDto summonerPlayDto,
                                     ChampionDto champion) {
        this.teamId = participant.getTeamId();
        this.summoner = summoner;
        this.championId = champion.getChampionId();
        this.championName = champion.getEnName();
        this.summonerPlayDto = summonerPlayDto;
        this.spellDId = participant.getSpell1Id();
        this.spellFId = participant.getSpell2Id();
        this.perks = Perk.getPerk(participant.getPerks());
    }
}
