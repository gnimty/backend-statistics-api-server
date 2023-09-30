package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.service.StatisticsService;
import onlysolorank.apiserver.api.service.dto.ChampionStatBriefDto;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : ChampionController
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 * 2023/09/13        solmin       전체 챔피언 및 championId를 통한 챔피언 검색
 * 2023/09/30        solmin       전체 챔피언 통계정보, 포지션별 챔피언 티어정보 개발
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/statistics/champion")
public class ChampionController {

    private final StatisticsService statisticsService;

    @GetMapping("/stats/total")
    public List<ChampionStatBriefDto> getAllChampionStats(
        @RequestParam(value = "tier", defaultValue = "EMERALD") TierFilter tier,
        @RequestParam(value = "period", defaultValue = "DAY") Period period,
        @RequestParam(value = "position", defaultValue = "ALL") PositionFilter position) {

        List<ChampionStatBriefDto> result = statisticsService.getAllChampionStats(tier, period, position);

        return result;
    }

    @GetMapping("/stats/tier")
    public List<ChampionTierDto> getChampionDetail(
        @RequestParam(value = "position", defaultValue = "TOP") PositionFilter position) {

        if(position==PositionFilter.ALL){
            position = PositionFilter.TOP;
        }

        List<ChampionTierDto> result = statisticsService.getChampionDetail(position);

        return result;
    }

}
