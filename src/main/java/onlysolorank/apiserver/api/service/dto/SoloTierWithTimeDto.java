package onlysolorank.apiserver.api.service.dto;

import lombok.Data;
import onlysolorank.apiserver.domain.dto.History;
import onlysolorank.apiserver.domain.dto.Tier;

import java.time.ZonedDateTime;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SoloTierWithTimeDto
 * author         : solmin
 * date           : 2023/08/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/15        solmin       최초 생성
 * 2023/09/04        solmin       Builder 제거 (불필요)
 */
@Data
public class SoloTierWithTimeDto {
    private Tier tier;
    private Integer division;
    private Integer lp;
    private ZonedDateTime updatedAt;

    public SoloTierWithTimeDto(History history){
        this.tier = Tier.valueOf(history.getQueue());
        this.division = history.getTier();
        this.lp = history.getLeaguePoints();
        this.updatedAt = history.getUpdatedAt();
    }
}
