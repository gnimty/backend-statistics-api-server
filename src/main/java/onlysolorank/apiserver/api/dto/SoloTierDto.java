package onlysolorank.apiserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.Tier;

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
 * 2023/07/31        solmin       Getter 이름 일부 변경
 */

@Data
@AllArgsConstructor
public class SoloTierDto {
    private Tier tier;
    private Integer division;
    private Integer lp;

    @Builder
    public SoloTierDto(Summoner summoner){
        this.tier = Tier.valueOf(summoner.getQueue());
        this.division = summoner.getTier();
        this.lp = summoner.getLeaguePoints();
    }
}
