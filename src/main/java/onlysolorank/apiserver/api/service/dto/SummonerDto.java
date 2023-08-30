package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.SoloTierDto;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : SummonerDto
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/24        solmin       필드 이름 수정 및 @Builder 추가
 * 2023/08/30        solmin       총 승리/패배 및 플레이수 필드 추가
 */
@Data
@AllArgsConstructor
@Slf4j
public class SummonerDto {
    private String summonerName;
    private String internalName;
    private String summonerId;
    private String puuid;
    private Integer profileIconId;
    private Integer summonerLevel;
    private SoloTierDto soloTierInfo;
    private Integer totalWin;
    private Integer totalDefeat;
    private Integer totalPlays;

    @Builder
    public SummonerDto(Summoner summoner) {
        this.summonerName = summoner.getName();
        this.internalName = summoner.getInternalName();
        this.summonerId = summoner.getSummonerId();
        this.puuid = summoner.getPuuid();
        this.profileIconId = summoner.getProfileIconId();
        this.summonerLevel = summoner.getSummonerLevel();
        this.soloTierInfo = SoloTierDto.builder().summoner(summoner).build();
        this.totalWin = summoner.getTotalWin();
        this.totalDefeat = summoner.getTotalDefeat();

        if(totalWin==null || totalDefeat==null)
            this.totalPlays = null;
        else {
            this.totalPlays = totalWin + totalDefeat;
        }
    }

}
