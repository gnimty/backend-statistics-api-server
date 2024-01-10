package onlysolorank.apiserver.api.controller;

import static onlysolorank.apiserver.api.constant.ApiSummary.GET_CHAMPION_STATS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.constant.ApiDescription;
import onlysolorank.apiserver.api.constant.ApiSummary;
import onlysolorank.apiserver.api.controller.dto.ChampionSpecialistRes;
import onlysolorank.apiserver.api.controller.dto.SummonerRankPageRes;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import onlysolorank.apiserver.api.service.dto.SummonerPlayWithSummonerDto;
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
@Tag(name = "/statistics/rank", description = "소환사 점수별 또는 챔피언 플레이별 랭킹 정보 조회 컨트롤러")
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
    @Operation(summary = ApiSummary.GET_SUMMONER_RANK, description = ApiDescription.GET_SUMMONER_RANK)
    @Parameters({
        @Parameter(in = ParameterIn.QUERY, name = "page", description = "1부터 시작하는 페이지 번호로 1페이지당 최대 100명의 소환사 랭킹정보 노출",
            required = false, example = "1"),
        @Parameter(in = ParameterIn.QUERY, name = "size", description = "한 번에 가져올 페이지 사이즈로 1부터 100까지 지정 가능"
            , required = false, example = "30"),
        @Parameter(in = ParameterIn.QUERY, name = "queue_type", description = "검색할 큐 타입 정보로, RANK_SOLO 또는 RANK_FLEX만 지정 가능",
            required = false, example = "RANK_SOLO"),
    })
    public CommonResponse<SummonerRankPageRes> getSummonerRank(
        @RequestParam(value = "page", defaultValue = "1")
        @Positive(message = "page는 1보다 큰 값이어야 합니다.") Integer page,
        @RequestParam(value = "size", defaultValue = "100")
        @Min(value = 1, message = "size는 1 이상이어야 합니다.")
        @Max(value = 100, message = "size는 100 이하여야 합니다.") Integer size,
        @RequestParam(value = "queue_type", defaultValue = "RANK_SOLO") QueueType queueType) {

        List<QueueType> available = new ArrayList<>(Arrays.asList(QueueType.RANK_SOLO, QueueType.RANK_FLEX));

        if (!available.contains(queueType) ){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "queue_type은 RANK_SOLO, RANK_FLEX 중 하나여야 합니다.");
        }

        SummonerRankPageRes result = summonerService.getSummonerRankByMMR(page, queueType, size);

        return CommonResponse.success(result);
    }

    @GetMapping("/champion/{champion_name}")
    @Operation(summary = ApiSummary.GET_SPECIALISTS, description = ApiDescription.GET_SPECIALISTS, deprecated = true)
    public CommonResponse getSpecialists(
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
