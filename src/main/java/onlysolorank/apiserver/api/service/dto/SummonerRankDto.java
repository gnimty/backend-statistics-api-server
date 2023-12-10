package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.Position;
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
    private List<Long> mostPlayedChampionIds;
    private List<Position> mostLanes;

    private Integer rank;
    private SummonerTierDto tierInfo;

    @Builder
    public SummonerRankDto(Summoner summoner, Integer rank, QueueType queueType) {
        this.tierInfo = SummonerTierDto.from(summoner, queueType);
        this.summonerLevel = summoner.getSummonerLevel();
        this.profileIconId = summoner.getProfileIconId();

        if(queueType==QueueType.RANK_SOLO){
            this.mostPlayedChampionIds = summoner.getMostChampionIds();
            this.mostLanes = summoner.getMostLanes();

        }else if(queueType==QueueType.RANK_FLEX){
            this.mostPlayedChampionIds = summoner.getMostChampionIdsFlex();
            this.mostLanes = summoner.getMostLanesFlex();
        }else{
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "소환사 랭크 정보 QueueType parameter는 RANK_SOLO 또는 RANK_FLEX의 queueType만 받습니다.");
        }

        this.puuid = summoner.getPuuid();
        this.summonerName = summoner.getName();
        this.tagLine = summoner.getTagLine();
        this.internalTagName = summoner.getInternalTagName();
        this.rank = rank;
    }
}
