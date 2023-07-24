package onlysolorank.apiserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : SoloTierDto
 * author         : solmin
 * date           : 2023/07/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/24        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class SoloTierDto {
    private Tier tier;
    private Integer division;
    private Integer lp;

    @Builder
    public SoloTierDto(Summoner summoner){
        // 문제될 수도 있음
        this.tier = Tier.valueOf(summoner.getQueue());
        this.division = summoner.getDivision();
        this.lp = summoner.getLp();
    }
}
