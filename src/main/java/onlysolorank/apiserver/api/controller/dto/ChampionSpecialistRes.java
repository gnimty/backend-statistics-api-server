package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.SummonerPlayWithSummonerDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : SummonerMatchResponseDto
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Data
@AllArgsConstructor
@Builder
public class ChampionSpecialistRes {

    List<SummonerPlayWithSummonerDto> championPlays;
    private Integer championId;
    private String championName;
}
