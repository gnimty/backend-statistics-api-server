package onlysolorank.apiserver.api.controller;

import static onlysolorank.apiserver.api.constant.ApiSummary.GET_CHAMPION_DETAIL;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_CHAMPION_STATS;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_LATEST_VERSION;
import static onlysolorank.apiserver.domain.dto.Tier.diamond;
import static onlysolorank.apiserver.domain.dto.Tier.emerald;
import static onlysolorank.apiserver.domain.dto.Tier.master;
import static onlysolorank.apiserver.domain.dto.Tier.platinum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.constant.ApiDescription;
import onlysolorank.apiserver.api.constant.ApiSummary;
import onlysolorank.apiserver.api.controller.dto.ChampionAnalysisRes;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.StatisticsService;
import onlysolorank.apiserver.domain.dto.Lane;
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
@Tag(name = "/statistics/champion", description = "챔피언별 통계 및 세부 분석 정보 조회 컨트롤러")
public class ChampionController {

    private final StatisticsService statisticsService;

    // 당장은 개별 포지션에 구분되는 티어 리스트를 가져올 필요가 없어 보이므로 Response 변경
    // 추후 티어별로 구분하여 티어 정보도 가져올 예정
    @GetMapping("/stats")
    @Operation(summary = ApiSummary.GET_CHAMPION_STATS, description = ApiDescription.GET_CHAMPION_STATS)
    @Parameters({
        @Parameter(in = ParameterIn.QUERY, name = "tier", description = "검색하려는 티어대 정보,"
            + "만약 master를 선택할 경우, 마스터 이상에서 이루어진 게임 정보를 바탕으로 한 챔피언 요약 정보 조회",
            required = false, example = "emerald",
            schema = @Schema(type = "string",
                allowableValues = {"master", "diamond", "emerald", "platinum"} )),
        @Parameter(in = ParameterIn.QUERY, name = "brief", description = "true로 지정할 경우에 포지션 내에서 score가 가장 높은 상위 5개의 챔피언 티어 정보만 조회",
            required = false, example = "true"),
        @Parameter(in = ParameterIn.QUERY, name = "queue_type", description = "검색하려는 큐 정보",
            schema = @Schema(type = "string",
                allowableValues = {"RANK_SOLO", "RANK_FLEX", "ARAM"}),
            required = false, example = "RANK_SOLO")
    })
    public CommonResponse getChampionStats(
        @RequestParam(value = "tier", defaultValue = "emerald") Tier tier,
        @RequestParam(value = "brief", defaultValue = "false") Boolean brief,
        @RequestParam(value = "queue_type", defaultValue = "RANK_SOLO") QueueType queueType) {

        if(queueType==QueueType.ALL){
            queueType= QueueType.RANK_SOLO;
        }
        switch (queueType) {
            case RANK_SOLO:
            case RANK_FLEX:
                return CommonResponse.success(statisticsService.getChampionTierList(queueType, brief, tier));
            case ARAM:
                return CommonResponse.success(statisticsService.getChampionAramTierList(queueType, brief, tier));
        }

        return null;
    }

    @GetMapping("/stats/detail/{champion_name}")
    @Operation(summary = ApiSummary.GET_CHAMPION_DETAIL, description = ApiDescription.GET_CHAMPION_DETAIL)
    @Parameters({
        @Parameter(in = ParameterIn.QUERY, name = "tier", description = "검색하려는 티어대 정보, "
            + "만약 master 티어를 선택한 경우 마스터 이상에서 이루어진 게임 정보를 바탕으로 한 챔피언 요약 정보 조회",
            required = false, example = "emerald"),
        @Parameter(in = ParameterIn.QUERY, name = "lane", description = "검색하려는 포지션 정보로,"
            + "특정 라인을 지정하지 않거나 UNKNOWN 또는 ALL을 선택하면 통계에 자주 잡히는 라인 정보로 변경",
            required = false, example = "TOP"),
        @Parameter(in = ParameterIn.PATH, name = "champion_name", description = "검색하려는 챔피언 영문 이름, Case-sensitive",
            required = true, example = "Aatrox"),
    })
    public CommonResponse<ChampionAnalysisRes> getChampionDetail(
        @PathVariable("champion_name") String championName,
        @RequestParam(value = "lane", defaultValue = "UNKNOWN") Lane lane,
        @RequestParam(value = "tier", defaultValue = "emerald") Tier tier) {

        ChampionAnalysisRes result = statisticsService.getChampionAnalysis(championName, lane, tier);

        return CommonResponse.success(result);
    }

}
