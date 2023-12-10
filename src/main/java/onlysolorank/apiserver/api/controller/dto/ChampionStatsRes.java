package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.Builder;
import onlysolorank.apiserver.api.service.dto.ChampionTotalStatDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : ChampionStatsRes
 * author         : solmin
 * date           : 11/6/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/6/23        solmin       최초 생성
 */
@Builder
public class ChampionStatsRes {

    List<ChampionTotalStatDto> championStats;
    Period period;

}
