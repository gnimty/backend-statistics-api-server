package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.*;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.StatisticsService;
import onlysolorank.apiserver.domain.dto.Position;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * 2023/10/05        solmin       챔피언 분석정보 개발
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/statistics/champion")
public class ChampionController {

    private final StatisticsService statisticsService;

    // deprecated
//    @GetMapping("/stats/total")
//    public CommonResponse<ChampionStatsRes> getAllChampionStats(
//        @RequestParam(value = "tier", defaultValue = "emerald") Tier tier,
//        @RequestParam(value = "period", defaultValue = "DAY") Period period,
//        @RequestParam(value = "position", defaultValue = "ALL") Position position) {
//
//        List<ChampionTotalStatDto> result = statisticsService.getAllChampionStats(tier, period, position);
//
//        return CommonResponse.success(ChampionStatsRes.builder()
//                .championStats(result)
//                .build());
//    }

    // 당장은 개별 포지션에 구분되는 티어 리스트를 가져올 필요가 없어 보이므로 Response 변경
    // 추후 티어별로 구분하여 티어 정보도 가져올 예정
    @GetMapping("/stats")
    public CommonResponse getChampionStats(
        @RequestParam(value = "tier", defaultValue = "emerald") Tier tier,
        @RequestParam(value = "brief", defaultValue = "false") Boolean brief,
        @RequestParam(value = "mode", defaultValue = "RANK_SOLO") QueueType mode){

        switch(mode){
            case RANK_SOLO:
                ChampionTierRes result = statisticsService.getChampionTierList(brief, tier);
                return CommonResponse.success(result);
            case RANK_FLEX:
            case ARAM:
            case BLIND:
                break;
        }

        return null;
    }

    @GetMapping("/stats/detail/{champion_name}")
    public CommonResponse<ChampionAnalysisRes> getSpecificChampionDetail(
        @PathVariable("champion_name") String championName,
        @RequestParam(value = "position", defaultValue = "UNKNOWN") Position position,
        @RequestParam(value = "tier", defaultValue = "emerald") Tier tier) {

        ChampionAnalysisRes result = statisticsService.getChampionAnalysis(championName, position, tier);

        return CommonResponse.success(result);
    }

}
