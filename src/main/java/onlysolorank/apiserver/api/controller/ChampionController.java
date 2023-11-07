package onlysolorank.apiserver.api.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.*;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.AssetService;
import onlysolorank.apiserver.api.service.StatisticsService;
import onlysolorank.apiserver.api.service.dto.ChampionTotalStatDto;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
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
    private final AssetService assetService;

    @GetMapping("/stats/total")
    public CommonResponse<ChampionStatsRes> getAllChampionStats(
        @RequestParam(value = "tier", defaultValue = "EMERALD") TierFilter tier,
        @RequestParam(value = "period", defaultValue = "DAY") Period period,
        @RequestParam(value = "position", defaultValue = "ALL") PositionFilter position) {

        List<ChampionTotalStatDto> result = statisticsService.getAllChampionStats(tier, period, position);

        return CommonResponse.success(ChampionStatsRes.builder()
                .championStats(result)
                .build());
    }

    @GetMapping("/stats/tier")
    public CommonResponse<ChampionTierRes> getChampionTierList(
        @RequestParam(value = "position", defaultValue = "ALL") PositionFilter position,
        @RequestParam(value = "brief", defaultValue = "false") Boolean brief) {

        if (position == PositionFilter.ALL) {
            position = PositionFilter.TOP;
        } else if (position == PositionFilter.UNKNOWN) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "정확한 포지션 정보를 입력하세요.");
        }

        List<ChampionTierDto> results = statisticsService.getChampionTierList(position, brief);

        return CommonResponse.success(ChampionTierRes.builder()
            .position(position)
            .version(assetService.getLatestVersionString())
            .champions(results)
            .build());
    }

    @GetMapping("/stats/detail/{champion_name}")
    public CommonResponse<ChampionAnalysisRes> getSpecificChampionDetail(
        @PathVariable("champion_name") String championName,
        @RequestParam(value = "position", defaultValue = "UNKNOWN") PositionFilter position,
        @RequestParam(value = "tier", defaultValue = "EMERALD") TierFilter tier) {

        ChampionAnalysisRes result = statisticsService.getChampionAnalysis(championName, position, tier);

        return CommonResponse.success(result);
    }

}
