package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.KeywordRequestDto;
import onlysolorank.apiserver.api.controller.dto.SummonerMatchResponseDto;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

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
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerController {
    private final SummonerService summonerService;

    /**
     * Gets summoner by internal name.
     *
     * @param keywordRequestDto the keyword request dto
     * @return the summoner by internal name
     */
    @GetMapping("/summoners/autocomplete")
    public CommonResponse<List<SummonerDto>> getSummonerByInternalName(@ModelAttribute @Valid KeywordRequestDto keywordRequestDto) {
        List<SummonerDto> data = summonerService.getSummonerDtoListByInternalName(keywordRequestDto.getInternalName());
        return CommonResponse.success(data);
    }

    /**
     * Gets summoner match info by summoner name.
     *
     * @param summonerName the summoner name
     * @param lastMatchId  the last match id
     * @return the summoner match info by summoner name
     */
    @GetMapping("/summoners/matches/{summoner_name}")
    public CommonResponse getSummonerMatchInfoBySummonerName(@PathVariable("summoner_name") String summonerName,
                                                             @RequestParam("ended") Optional<String> lastMatchId) {

        // TODO lastMatchId 검증 필요
        if (!lastMatchId.isPresent() || lastMatchId.get().isBlank()) {
            SummonerMatchResponseDto data = summonerService.getSummonerMatchInfoBySummonerName(summonerName);
            return CommonResponse.success(data);
        }
        List<MatchDto> data = summonerService.get20MatchesByLastMatchId(summonerName, lastMatchId.get());

        return CommonResponse.success(data);
    }

    /**
     * Gets all champion play info by puuid.
     *
     * @param puuid the puuid
     * @return the all champion play info by puuid
     */
    @GetMapping("/summoners/champion/{puuid}")
    public CommonResponse<List<ChampionPlaysBriefDto>> getAllChampionPlayInfoByPuuid(@PathVariable("puuid") String puuid){
        List<ChampionPlaysBriefDto> data = summonerService.getAllChampionPlayInfoByPuuid(puuid);

        return CommonResponse.success(data);
    }

    /**
     * Gets summoner history.
     *
     * @param puuid the puuid
     * @return List<SoloTierWithTimeDto>
     */
    @GetMapping("/summoners/tier/{puuid}")
    public CommonResponse<List<SoloTierWithTimeDto>> getSummonerHistory(@PathVariable("puuid") String puuid){
        List<SoloTierWithTimeDto> data = summonerService.getSummonerHistory(puuid);

        return CommonResponse.success(data);
    }
  
    /**
     * Gets summoner rank.
     *
     * @param page the page
     * @return the summoner rank
     */
    @GetMapping("/entries/tier")
    public CommonResponse getSummonerRank(@RequestParam(value = "page", defaultValue = "0") @PositiveOrZero(message = "page는 0보다 큰 값이어야 합니다.") Integer page){
        SummonerRankPageDto summonerRankByMMR = summonerService.getSummonerRankByMMR(page);

        return CommonResponse.success(summonerRankByMMR);
    }

}
