package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : ChampionTierRes
 * author         : solmin
 * date           : 11/1/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/1/23        solmin       최초 생성
 */

@Data
@Builder
@AllArgsConstructor
public class ChampionAramTierRes {

    private String version;
    private List<ChampionTierDto> results;
}
