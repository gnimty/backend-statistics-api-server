package onlysolorank.apiserver.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.ChampionDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : ChampionRes
 * author         : solmin
 * date           : 11/6/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/6/23        solmin       최초 생성
 */

@Data
@Builder
public class ChampionRes {

    private List<ChampionDto> champions;

    private ChampionRes(List<ChampionDto> champions) {
        this.champions = champions;
    }

}
