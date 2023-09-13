package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.ChampionSaleRes;
import onlysolorank.apiserver.api.controller.dto.SkinSaleRes;
import onlysolorank.apiserver.api.controller.dto.VersionRes;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.CrawlService;
import onlysolorank.apiserver.domain.Champion;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : CrawlController
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/crawl")
public class CrawlController {
    private final CrawlService crawlService;

    @GetMapping("/version")
    public CommonResponse<VersionRes> getLatestVersion(){
        String latestVersion = crawlService.getLatestVersion();

        return CommonResponse.success(new VersionRes(latestVersion));
    }

    @GetMapping("/sale/champion")
    public CommonResponse<List<ChampionSaleRes>> getChampionSalesInfo(){
        List<ChampionSaleRes> allChampionSalesInfo = crawlService.getAllChampionSalesInfo()
            .stream()
            .map(ChampionSaleRes::new)
            .toList();

        return CommonResponse.success(allChampionSalesInfo);
    }

    @GetMapping("/sale/skin")
    public CommonResponse<List<SkinSaleRes>> getSkinSalesInfo(){
        List<SkinSaleRes> allSkinSalesInfo = crawlService.getAllSkinSalesInfo()
            .stream()
            .map(SkinSaleRes::new)
            .toList();

        return CommonResponse.success(allSkinSalesInfo);
    }

    @GetMapping("/champion")
    public CommonResponse<List<Champion>> getAllChampion(){
        List<Champion> result = crawlService.getAllChampions();
        return CommonResponse.success(result);
    }

    @GetMapping("/champion/{champion_id}")
    public CommonResponse<Champion> getAllChampion(@PathVariable("champion_id") Long championId){
        Champion result = crawlService.getChamiponByChampionId(championId);

        return CommonResponse.success(result);
    }

}
