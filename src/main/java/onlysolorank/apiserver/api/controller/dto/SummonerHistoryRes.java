package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.SummonerTierDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : SummonerHistoryRes
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
public class SummonerHistoryRes {

    List<SummonerTierDto> histories;
}
