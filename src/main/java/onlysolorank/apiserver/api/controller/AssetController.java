package onlysolorank.apiserver.api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/version")
    public CommonResponse<VersionRes> getLatestVersion() {
        return CommonResponse.success(assetService.getLatestVersion());
    }

    @GetMapping("/sale/champion")
    public CommonResponse<ChampionSaleRes> getChampionSales() {
        List<ChampionSaleDto> results = assetService.getAllChampionSalesInfo();

        return CommonResponse.success(ChampionSaleRes.builder()
                .championSales(results)
                .build()
        );
    }

    @GetMapping("/sale/skin")
    public CommonResponse<SkinSaleRes> getSkinSales() {
        return CommonResponse.success(SkinSaleRes.builder()
                .skinSales(assetService.getAllSkinSalesInfo())
                .build());
    }

    @GetMapping("/champion")
    public CommonResponse<ChampionRes> getAllChampion() {
        List<ChampionDto> results = assetService.getAllChampions();

        return CommonResponse.success(ChampionRes.multiBuilder()
                .champions(results)
                .build());
    }

    @GetMapping("/champion/{champion_id}")
    public CommonResponse<ChampionRes> getChampion(@PathVariable("champion_id") Long championId) {
        ChampionDto result = assetService.getChampion(championId);

        return CommonResponse.success(ChampionRes.singleBuilder()
                .champion(result)
                .build()
        );
    }

    @GetMapping("/rotation")
    public CommonResponse<ChampionRes> getRotations() {
        List<ChampionDto> results = assetService.getRotationChampions();

        return CommonResponse.success(ChampionRes.multiBuilder()
                .champions(results)
                .build());
    }

    @PatchMapping("/champion")
    public CommonResponse initChampions() {
        assetService.updateChampionCache();

        return CommonResponse.success("챔피언 맵 갱신에 성공하였습니다.");
    }

}
