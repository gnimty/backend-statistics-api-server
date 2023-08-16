package onlysolorank.apiserver.domain.dto;

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
 * 2023/07/31        solmin       Getter 이름 일부 변경
 * 2023/08/16        solmin       getLeaguePoints to getLp (Summoner 도큐먼트 필드명 변경)

 */

@Data
@AllArgsConstructor
public class SoloTierDto {
    private Tier tier;
    private Integer division;
    private Integer lp;

    @Builder
    public SoloTierDto(Summoner summoner) {
        this.tier = Tier.valueOf(summoner.getQueue());
        this.division = summoner.getTier();
        this.lp = summoner.getLp();
    }
}
