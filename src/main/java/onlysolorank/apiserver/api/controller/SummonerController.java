package onlysolorank.apiserver.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.AutoCompleteRes;
import onlysolorank.apiserver.api.controller.dto.CurrentGameRes;
import onlysolorank.apiserver.api.controller.dto.KeywordReq;
import onlysolorank.apiserver.api.controller.dto.MatchDetailRes;
import onlysolorank.apiserver.api.controller.dto.RecentMemberRes;
import onlysolorank.apiserver.api.controller.dto.SummonerMatchRes;
import onlysolorank.apiserver.api.controller.dto.SummonerPlayRes;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import onlysolorank.apiserver.api.service.dto.RecentMemberDto;
import onlysolorank.apiserver.api.service.dto.SummonerDto;
import onlysolorank.apiserver.api.service.dto.SummonerPlayDto;
import onlysolorank.apiserver.domain.dto.QueueType;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/autocomplete")
    public CommonResponse<AutoCompleteRes> getAutoCompleteResults(
        @ModelAttribute @Valid KeywordReq keywordReq) {

        String internalTagName = keywordReq.keyword();

        List<SummonerDto> results = summonerService.getTop5SummonersByInternalTagName(internalTagName);

        return CommonResponse.success(AutoCompleteRes.builder()
            .summoners(results)
            .keyword(internalTagName)
            .build());
    }

    @GetMapping("/{summoner_tag_name}")
    public CommonResponse<SummonerDto> getSummoner(
        @PathVariable("summoner_tag_name") String summonerTagName) {

        SummonerDto result = SummonerDto.from(summonerService.getSummonerBySummonerTagName(summonerTagName));

        return CommonResponse.success(result);
    }


    @GetMapping("/matches/{summoner_tag_name}")
    public CommonResponse<SummonerMatchRes> getSummonerMatchInfoBySummonerName(
        @PathVariable("summoner_tag_name") String summonerTagName,
        @RequestParam("ended") Optional<String> lastMatchId,
        @RequestParam(value = "queue_type", defaultValue = "ALL") QueueType queueType) {

        SummonerMatchRes result;

        if (lastMatchId.isEmpty() || lastMatchId.get().isBlank()) {
            result = summonerService.getSummonerMatchInfoBySummonerName(summonerTagName, queueType);
        } else {
            result = SummonerMatchRes.builder()
                .matches(summonerService.get20MatchesByOptionalLastMatchId(summonerTagName, lastMatchId.get(), queueType))
                .build();
        }

        return CommonResponse.success(result);
    }

    @GetMapping("/matches/id/{match_id}")
    public CommonResponse<MatchDetailRes> getMatchDetailByMatchId(
        @PathVariable("match_id") String matchId) {
        return CommonResponse.success(summonerService.getMatchDetail(matchId));
    }


    /**
     * Gets all champion play info by puuid.
     *
     * @param summonerName the summoner name
     * @return the all champion play info by puuid
     */
    @GetMapping("/champion/{summoner_tag_name}")
    public CommonResponse<SummonerPlayRes> getAllChampionPlayInfoBySummonerName(
        @PathVariable("summoner_tag_name") String summonerTagName,
        @RequestParam(value = "queue_type", defaultValue = "ALL") QueueType queueType,
        @RequestParam(value = "brief", defaultValue = "false") Boolean brief) {

        List<QueueType> available = new ArrayList<>(Arrays.asList(QueueType.ALL, QueueType.RANK_SOLO, QueueType.RANK_FLEX));

        if (!available.contains(queueType) ){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "queue_type은 ALL, RANK_SOLO, RANK_FLEX 중 하나여야 합니다.");
        }

        List<SummonerPlayDto> results = summonerService.getChampionPlayInfo(
            summonerTagName, queueType, brief);

        return CommonResponse.success(SummonerPlayRes.builder()
            .summonerPlays(results)
            .build());
    }

//    /**
//     * Gets summoner history.
//     *
//     * @param summonerName the summoner name
//     * @return the summoner history
//     */
    // deprecated
//    @GetMapping("/tier/{summoner_name}")
//    public CommonResponse<SummonerHistoryRes> getSummonerHistory(
//        @PathVariable("summoner_name") String summonerName) {
//        List<SummonerTierDto> results = summonerService.getSummonerHistory(summonerName);
//
//        return CommonResponse.success(SummonerHistoryRes.builder()
//            .histories(results)
//            .build());
//    }

    @PostMapping("/{puuid}")
    public CommonResponse refreshSummoner(@PathVariable("puuid") String puuid) {
        summonerService.refreshSummoner(puuid);

        return CommonResponse.success("소환사 정보를 성공적으로 갱신했습니다.", HttpStatus.OK);
    }

    @GetMapping("/ingame/{summoner_tag_name}")
    public CommonResponse<CurrentGameRes> getCurrentGame(@PathVariable("summoner_tag_name") String summonerTagName) {
        return CommonResponse.success(summonerService.getCurrentGame(summonerTagName));
    }


    @GetMapping("/together/{summoner_tag_name}")
    public CommonResponse<RecentMemberRes> getRecentMembers(
        @PathVariable("summoner_tag_name") String summonerTagName,
        @RequestParam(value = "queue_type", defaultValue = "RANK_SOLO") QueueType queueType) {

        List<QueueType> available = new ArrayList<>(Arrays.asList(QueueType.RANK_SOLO, QueueType.RANK_FLEX));

        if (!available.contains(queueType) ){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "queue_type은 RANK_SOLO, RANK_FLEX 중 하나여야 합니다.");
        }

        List<RecentMemberDto> results = summonerService.getRecentMemberInfo(summonerTagName, queueType);

        return CommonResponse.success(RecentMemberRes.builder()
            .recentMembers(results)
            .build());
    }


}
