package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.SummonerPlayDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : SummonerPlayRes
 * author         : solmin
 * date           : 11/6/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/6/23        solmin       최초 생성
 */
@Data
@AllArgsConstructor
@Builder
public class SummonerPlayRes {

    List<SummonerPlayDto> summonerPlays;
}
