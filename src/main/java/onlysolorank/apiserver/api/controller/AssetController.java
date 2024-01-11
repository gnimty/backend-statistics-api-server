package onlysolorank.apiserver.api.controller;

import static onlysolorank.apiserver.api.constant.ApiSummary.GET_ALL_CHAMPIONS;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_CHAMPION;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_CHAMPION_SALES;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_LATEST_VERSION;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_ROTATIONS;
import static onlysolorank.apiserver.api.constant.ApiSummary.GET_SKIN_SALES;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.constant.ApiDescription;
import onlysolorank.apiserver.api.constant.ApiSummary;
import onlysolorank.apiserver.api.controller.dto.ChampionRes;
import onlysolorank.apiserver.api.controller.dto.ChampionSaleRes;
import onlysolorank.apiserver.api.controller.dto.SkinSaleRes;
import onlysolorank.apiserver.api.controller.dto.VersionRes;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.AssetService;
import onlysolorank.apiserver.api.service.dto.ChampionDto;
import onlysolorank.apiserver.api.service.dto.ChampionSaleDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : AssetController
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 * 2023/10/09        solmin       Prefix Asset으로 변경
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/asset")
@Tag(name = "/asset", description = "라이엇 정적 데이터 및 크롤링 데이터 정보 조회 컨트롤러")
public class AssetController {

    private final AssetService assetService;


    @Operation(summary = ApiSummary.GET_LATEST_VERSION, description = ApiDescription.GET_LATEST_VERSION)
    @GetMapping("/version")
    public CommonResponse<VersionRes> getLatestVersion() {
        return CommonResponse.success(assetService.getLatestVersion());
    }

    @Operation(summary = ApiSummary.GET_CHAMPION_SALES, description = ApiDescription.GET_CHAMPION_SALES)
    @GetMapping("/sale/champion")
    public CommonResponse<ChampionSaleRes> getChampionSales() {
        List<ChampionSaleDto> results = assetService.getAllChampionSalesInfo();

        return CommonResponse.success(ChampionSaleRes.builder()
            .championSales(results)
            .build()
        );
    }

    @Operation(summary = ApiSummary.GET_SKIN_SALES, description = ApiDescription.GET_SKIN_SALES)
    @GetMapping("/sale/skin")
    public CommonResponse<SkinSaleRes> getSkinSales() {
        return CommonResponse.success(SkinSaleRes.builder()
            .skinSales(assetService.getAllSkinSalesInfo())
            .build());
    }

    @Operation(summary = ApiSummary.GET_ALL_CHAMPIONS, description = ApiDescription.GET_ALL_CHAMPIONS)
    @GetMapping("/champion")
    public CommonResponse<ChampionRes> getAllChampion() {
        List<ChampionDto> results = assetService.getAllChampions();

        return CommonResponse.success(ChampionRes.builder()
            .champions(results)
            .build());
    }

    @Operation(summary = ApiSummary.GET_CHAMPION, description = ApiDescription.GET_CHAMPION)
    @Parameter(in = ParameterIn.PATH, name = "champion_id", description = "조회할 champion ID", required = true, example = "266")
    @GetMapping("/champion/{champion_id}")
    public CommonResponse<ChampionDto> getChampion(@PathVariable("champion_id") Long championId) {
        ChampionDto result = assetService.getChampion(championId);

        return CommonResponse.success(result);
    }

    @Operation(summary = ApiSummary.GET_ROTATIONS, description = ApiDescription.GET_ROTATIONS)
    @GetMapping("/rotation")
    public CommonResponse<ChampionRes> getRotations() {
        List<ChampionDto> results = assetService.getRotationChampions();

        return CommonResponse.success(ChampionRes.builder()
            .champions(results)
            .build());
    }

    @PatchMapping("/champion")
    @Operation(hidden = true)
    public CommonResponse initChampions() {
        assetService.updateChampionCache();

        return CommonResponse.success("챔피언 맵 갱신에 성공하였습니다.");
    }

}
