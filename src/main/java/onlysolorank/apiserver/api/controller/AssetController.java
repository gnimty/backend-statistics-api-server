package onlysolorank.apiserver.api.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.ChampionSaleRes;
import onlysolorank.apiserver.api.controller.dto.SkinSaleRes;
import onlysolorank.apiserver.api.controller.dto.VersionRes;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.AssetService;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.Rotation;
import onlysolorank.apiserver.domain.Version;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
        Version latestVersion = assetService.getLatestVersion();

        return CommonResponse.success(new VersionRes(latestVersion));
    }

    @GetMapping("/sale/champion")
    public CommonResponse<List<ChampionSaleRes>> getChampionSalesInfo() {
        List<ChampionSaleRes> allChampionSalesInfo = assetService.getAllChampionSalesInfo()
            .stream()
            .map(ChampionSaleRes::new)
            .toList();

        return CommonResponse.success(allChampionSalesInfo);
    }

    @GetMapping("/sale/skin")
    public CommonResponse<List<SkinSaleRes>> getSkinSalesInfo() {
        List<SkinSaleRes> allSkinSalesInfo = assetService.getAllSkinSalesInfo()
            .stream()
            .map(SkinSaleRes::new)
            .toList();

        return CommonResponse.success(allSkinSalesInfo);
    }

    @GetMapping("/champion")
    public CommonResponse<List<Champion>> getAllChampion() {
        List<Champion> result = assetService.getAllChampions();
        return CommonResponse.success(result);
    }

    @GetMapping("/champion/{champion_id}")
    public CommonResponse<Champion> getAllChampion(@PathVariable("champion_id") Long championId) {
        Champion result = assetService.getChamiponByChampionId(championId);

        return CommonResponse.success(result);
    }

    @GetMapping("/rotation")
    public CommonResponse<List<Rotation>> getRotationChampions() {
        List<Rotation> result = assetService.getRotationChampions();

        return CommonResponse.success(result);
    }

}
