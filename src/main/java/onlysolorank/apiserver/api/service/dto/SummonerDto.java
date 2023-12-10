package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.QueueType;

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
    private String tagLine;
    private String internalTagName;
    private String puuid;
    private String summonerId;
    private Integer profileIconId;
    private Integer summonerLevel;

    private SummonerTierDto soloTierInfo;
    private SummonerTierDto flexTierInfo;


    public static SummonerDto from(Summoner summoner) {

        SummonerTierDto soloTier = SummonerTierDto.from(summoner, QueueType.RANK_SOLO);
        SummonerTierDto flexTier = SummonerTierDto.from(summoner, QueueType.RANK_FLEX);

        return SummonerDto.builder()
            .summonerName(summoner.getName())
            .internalName(summoner.getInternalName())
            .internalTagName(summoner.getInternalTagName())
            .tagLine(summoner.getTagLine())
            .summonerId(summoner.getSummonerId())
            .puuid(summoner.getPuuid())
            .profileIconId(summoner.getProfileIconId())
            .summonerLevel(summoner.getSummonerLevel())
            .soloTierInfo(soloTier)
            .flexTierInfo(flexTier)
            .build();
    }

}
