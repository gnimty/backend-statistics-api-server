package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.QueueType;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SummonerRankDto
 * author         : solmin
 * date           : 2023/08/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/15        solmin       최초 생성
 */

@Data
public class SummonerRankDto {

    private String puuid;
    private String summonerName;
    private String tagLine;
    private String internalTagName;
    private Integer summonerLevel;
    private Integer profileIconId;

    private Integer rank;
    private SummonerTierDto tierInfo;

    @Builder
    public SummonerRankDto(Summoner summoner, Integer rank, QueueType queueType) {
        this.tierInfo = SummonerTierDto.from(summoner, queueType);
        this.summonerLevel = summoner.getSummonerLevel();
        this.profileIconId = summoner.getProfileIconId();

        this.puuid = summoner.getPuuid();
        this.summonerName = summoner.getName();
        this.tagLine = summoner.getTagLine();
        this.internalTagName = summoner.getInternalTagName();
        this.rank = rank;
    }
}
