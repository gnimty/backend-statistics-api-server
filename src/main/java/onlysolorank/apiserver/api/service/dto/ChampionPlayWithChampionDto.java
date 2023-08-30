package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : ChampionPlayWithChampionDto
 * author         : solmin
 * date           : 2023/08/22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/22        solmin       최초 생성
 */
@Data
@ToString
public class ChampionPlayWithChampionDto {
    private Integer championId;
    private String championName;
    private ChampionPlaysDto championPlay;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer rank;

    @Builder
    public ChampionPlayWithChampionDto(ChampionPlaysDto championPlay, Integer rank){
        this.championPlay = championPlay;
        this.championId = championPlay.getChampionId();
        this.championName = championPlay.getChampionName();
        this.rank = rank;
    }

}