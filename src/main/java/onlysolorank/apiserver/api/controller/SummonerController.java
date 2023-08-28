package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.HasPlayedRes;
import onlysolorank.apiserver.api.controller.dto.KeywordReq;
import onlysolorank.apiserver.api.controller.dto.SummonerMatchRes;
import onlysolorank.apiserver.api.controller.dto.SummonerNamePairReq;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static onlysolorank.apiserver.utils.CustomConverter.keywordToInternalName;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : SummonerController
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/24        solmin       ModelAttribute로 쿼리 파라미터 DTO 변환 후 검증하는 로직 추가
 * 2023/07/31        solmin       소환사 정보 가져오기 API 작업 중
 * 2023/08/09        solmin       소환사 정보 가져오기 API 작업 완료
 * 2023/08/09        solmin       Javadocs 추가 및 소환사 전적정보 챔피언별 세부 조회 구현
 * 2023/08/15        solmin       소환사 랭크 티어 그래프 조회 컨트롤러 구현
 * 2023/08/16        solmin       소환사 랭크 조회 컨트롤러 구현
 * 2023/08/16        solmin       같이 플레이한 소환사인지 여부 구현 중
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/summoners")
public class SummonerController {
    private final SummonerService summonerService;

    @Value("${admin_db}")
    private static String admin;

    /**
     * Gets summoner by internal name.
     *
     * @param keywordReq the keyword request dto
     * @return the summoner by internal name
     */
    @GetMapping("/autocomplete")
    public CommonResponse<List<SummonerDto>> getSummonerByInternalName(@ModelAttribute @Valid KeywordReq keywordReq) {
        String internalName = keywordReq.getKeyword();

        List<SummonerDto> data = summonerService.getSummonerDtoListByInternalName(internalName);
        return CommonResponse.success(data);
    }

    /**
     * Gets summoner match info by summoner name.
     *
     * @param summonerName the summoner name
     * @param lastMatchId  the last match id
     * @return the summoner match info by summoner name
     */
    @GetMapping("/matches/{summoner_name}")
    public CommonResponse getSummonerMatchInfoBySummonerName(@PathVariable("summoner_name") String summonerName,
                                                             @RequestParam("ended") Optional<String> lastMatchId) {

        String internalName = keywordToInternalName(summonerName);

        // TODO lastMatchId 검증 필요
        if (!lastMatchId.isPresent() || lastMatchId.get().isBlank()) {
            SummonerMatchRes data = summonerService.getSummonerMatchInfoByInternalName(internalName);
            return CommonResponse.success(data);
        }
        List<MatchDto> data = summonerService.get20MatchesByLastMatchId(internalName, lastMatchId.get());

        return CommonResponse.success(data);
    }

    /**
     * Gets all champion play info by puuid.
     *
     * @param summonerName the summoner name
     * @return the all champion play info by puuid
     */
    @GetMapping("/champion/{summoner_name}")
    public CommonResponse<List<ChampionPlayWithChampionDto>> getAllChampionPlayInfoByPuuid(@PathVariable("summoner_name") String summonerName){

        String internalName = keywordToInternalName(summonerName);

        List<ChampionPlayWithChampionDto> data = summonerService.getAllChampionPlayInfoByPuuid(internalName);

        return CommonResponse.success(data);
    }

    /**
     * Gets summoner history.
     *
     * @param puuid the puuid
     * @return List<SoloTierWithTimeDto>
     */
    @GetMapping("/tier/{puuid}")
    public CommonResponse<List<SoloTierWithTimeDto>> getSummonerHistory(@PathVariable("puuid") String puuid){
        log.info("{}",admin);
        List<SoloTierWithTimeDto> data = summonerService.getSummonerHistory(puuid);

        return CommonResponse.success(data);
    }


    @GetMapping("/together/pair")
    public CommonResponse<HasPlayedRes> hasPlayedTogether(@ModelAttribute @Valid SummonerNamePairReq summonerNamePairReq) {
        String myName = summonerNamePairReq.getMyName();
        String friendName = summonerNamePairReq.getFriendName();

        HasPlayedRes data = summonerService.hasPlayedTogether(myName, friendName);
        return CommonResponse.success(data);
    }
}
