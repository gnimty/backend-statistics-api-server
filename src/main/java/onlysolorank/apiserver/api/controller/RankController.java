package onlysolorank.apiserver.api.controller;

import java.util.List;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.ChampionSpecialistRes;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import onlysolorank.apiserver.api.service.dto.SummonerPlayWithSummonerDto;
import onlysolorank.apiserver.api.controller.dto.SummonerRankPageRes;
import onlysolorank.apiserver.domain.dto.Tier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/statistics/rank")
public class RankController {

    private final static Integer STD_MMR = 2400;
    private final SummonerService summonerService;

    /**
     * Gets summoner rank.
     *
     * @param page the page
     * @return the summoner rank
     */
    @GetMapping("/tier")
    public CommonResponse<SummonerRankPageRes> getSummonerRank(
        @RequestParam(value = "page", defaultValue = "1") @Positive(message = "page는 1보다 큰 값이어야 합니다.") Integer page) {
        SummonerRankPageRes result = summonerService.getSummonerRankByMMR(page);

        return CommonResponse.success(result);
    }

    @GetMapping("/champion/{champion_name}")
    public CommonResponse getSpecialistsByChampionName(
        @RequestParam(value = "queue", defaultValue = "master") String queue,
        @PathVariable("champion_name") String championName) {

        // TODO redis 연동 후 챔피언아이디 검증해야 함
        Integer testChampionId = 30;

        try {
            Tier stdTier = Tier.valueOf(queue);
            // 다이아 이상의 티어 정보 호출이 아니라면 Exception
            if (stdTier.getBasisMMR() < STD_MMR) {
                throw new IllegalArgumentException();
            }
            List<SummonerPlayWithSummonerDto> result = summonerService.getSpecialistsByChampionName(
                championName, stdTier);

            return CommonResponse.success(ChampionSpecialistRes
                .builder()
                .championId(testChampionId).championName(championName).championPlays(result)
                .build());

        } catch (IllegalArgumentException ex) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE,
                "queue 정보가 diamond 미만이거나 잘못된 정보를 입력하였습니다.");
        }
    }


}
