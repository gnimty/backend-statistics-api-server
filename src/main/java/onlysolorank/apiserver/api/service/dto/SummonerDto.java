package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;

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
@Builder
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

    public static SummonerDto from(Summoner summoner) {
        Integer totalPlays = null;
        Integer totalWin = summoner.getTotalWin();
        Integer totalDefeat = summoner.getTotalDefeat();

        SoloTierDto soloTier = SoloTierDto.from(summoner);

        if (summoner.getTotalWin() != null && summoner.getTotalDefeat() != null) {
            totalPlays = totalWin + totalDefeat;
        }

        return SummonerDto.builder()
                .summonerName(summoner.getName())
                .internalName(summoner.getInternalName())
                .summonerId(summoner.getSummonerId())
                .puuid(summoner.getPuuid())
                .profileIconId(summoner.getProfileIconId())
                .summonerLevel(summoner.getSummonerLevel())
                .soloTierInfo(soloTier)
                .totalWin(totalWin)
                .totalDefeat(totalDefeat)
                .totalPlays(totalPlays)
                .build();
    }

}
