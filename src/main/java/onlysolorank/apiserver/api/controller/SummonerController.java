package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.*;
import onlysolorank.apiserver.api.service.dto.*;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
 * 2023/08/16        solmin       같이 플레이한 소환사인지 여부 구현 중
 * 2023/08/29        solmin       소환사 전적갱신 배치서버와 연동
 * 2023/08/30        solmin       소환사 인게임 정보 로드 연동
 * 2023/09/11        solmin       최근 20게임 플레이한 같은 팀 소환사 리스트 가져오기 추가
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/statistics/summoners")
public class SummonerController {
    private final SummonerService summonerService;

    /**
     * Gets summoner by internal name.
     *
     * @param keywordReq the keyword request dto
     * @return the summoner by internal name
     */
    @GetMapping("/autocomplete")
    public CommonResponse<List<SummonerDto>> getSummonerByInternalName(@ModelAttribute @Valid KeywordReq keywordReq) {
        String internalName = keywordReq.getInternalName();

        List<SummonerDto> data = summonerService.getTop5SummonerDtoListByInternalName(internalName);
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
        // TODO lastMatchId 검증 필요
        if (!lastMatchId.isPresent() || lastMatchId.get().isBlank()) {
            SummonerMatchRes data = summonerService.getSummonerMatchInfoBySummonerName(summonerName);
            return CommonResponse.success(data);
        } else{
            List<MatchDto> data = summonerService.get20MatchesByLastMatchId(summonerName, lastMatchId.get());
            return CommonResponse.success(data);
        }
    }

    /**
     * Gets all champion play info by puuid.
     *
     * @param summonerName the summoner name
     * @return the all champion play info by puuid
     */
    @GetMapping("/champion/{summoner_name}")
    public CommonResponse<List<SummonerPlayDto>> getAllChampionPlayInfoBySummonerName(@PathVariable("summoner_name") String summonerName){

        List<SummonerPlayDto> data = summonerService.getAllChampionPlayInfoBySummonerName(summonerName);

        return CommonResponse.success(data);
    }

    /**
     * Gets summoner history.
     *
     * @param summonerName the summoner name
     * @return the summoner history
     */
    @GetMapping("/tier/{summoner_name}")
    public CommonResponse<List<SoloTierWithTimeDto>> getSummonerHistory(@PathVariable("summoner_name") String summonerName){
        List<SoloTierWithTimeDto> data = summonerService.getSummonerHistory(summonerName);

        return CommonResponse.success(data);
    }

    @PostMapping("/{puuid}")
    public CommonResponse refreshSummoner(@PathVariable("puuid") String puuid){
        summonerService.refreshSummoner(puuid);

        return CommonResponse.success("소환사 정보를 성공적으로 갱신했습니다.", HttpStatus.OK);
    }

    @GetMapping("/ingame/{summoner_name}")
    public CommonResponse<IngameInfoRes> getIngameInfo(@PathVariable("summoner_name") String summonerName){
        IngameInfoRes data = summonerService.getIngameInfo(summonerName);

        return CommonResponse.success(data);
    }


    @GetMapping("/together/{summoner_name}")
    public CommonResponse<RecentMemberRes> getRecentMembers(@PathVariable("summoner_name") String summonerName) {
        List<RecentMemberDto> data = summonerService.getRecentMemberInfo(summonerName);

        return CommonResponse.success(new RecentMemberRes(data.size(), data));
    }


}
