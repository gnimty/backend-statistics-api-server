package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : ChampionPlaysBriefDto
 * author         : solmin
 * date           : 2023/08/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/09        solmin       최초 생성
 * 2023/08/10        solmin       totalDeath=0인 경우의 예외 처리를 위한 @JsonIgnore 필드 추가
 */

@Data
@ToString
public class SummonerPlayWithSummonerDto {

    private SummonerDto summoner;
    private SummonerPlayDto summonerPlay;
    private Integer rank;

    @Builder
    public SummonerPlayWithSummonerDto(SummonerDto summoner, SummonerPlayDto summonerPlay,
        Integer rank) {
        this.summoner = summoner;
        this.summonerPlay = summonerPlay;
        this.rank = rank;
    }

}
