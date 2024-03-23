package onlysolorank.apiserver.api.controller;

import static onlysolorank.apiserver.utils.CustomFunctions.keywordToInternalTagName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.constant.ApiDescription;
import onlysolorank.apiserver.api.constant.ApiSummary;
import onlysolorank.apiserver.api.controller.dto.AutoCompleteRes;
import onlysolorank.apiserver.api.controller.dto.CurrentGameRes;
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
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "/statistics/summoners", description = "소환사 관련 정보 조회")
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("/autocomplete")
    @Operation(summary = ApiSummary.GET_AUTOCOMPLETE, description = ApiDescription.GET_AUTOCOMPLETE)
    @Parameter(in = ParameterIn.QUERY, name = "keyword", description = "소환사 이름 검색어,"
            + "[소환사이름]#[태그라인] 형식", required = true, example = "hideonbush#kr1")
    public CommonResponse<AutoCompleteRes> getAutoCompleteResults(
        @RequestParam @NotBlank(message = "keyword는 internalTagName 기준으로 1글자 이상 입력해야 합니다.") String keyword) {

        String internalTagName = keywordToInternalTagName(keyword, false);

        if(StringUtils.isBlank(internalTagName)){
            throw new IllegalArgumentException("keyword가 올바르지 않습니다.");
        }

        List<SummonerDto> results = summonerService.getTop5SummonersByInternalTagName(internalTagName);

        return CommonResponse.success(AutoCompleteRes.builder()
            .summoners(results)
            .keyword(internalTagName)
            .build());
    }

    @GetMapping("/{summoner_tag_name}")
    @Operation(summary = ApiSummary.GET_SUMMONER, description = ApiDescription.GET_SUMMONER)
    @Parameter(in = ParameterIn.PATH, name = "summoner_tag_name", description = "조회할 소환사 태그네임, [소환사명]-[태그라인]",
        required = true, example = "김솔민-top")
    public CommonResponse<SummonerDto> getSummoner(
        @PathVariable("summoner_tag_name") String summonerTagName) {

        String internalTagName = keywordToInternalTagName(summonerTagName, true);

        Summoner summoner = summonerService.getSummonerByInternalTagName(internalTagName, true);
        SummonerDto result = SummonerDto.from(summoner);

        return CommonResponse.success(result);
    }


    @GetMapping("/matches/{summoner_tag_name}")
    @Operation(summary = ApiSummary.GET_SUMMONER_MATCH_INFO, description = ApiDescription.GET_SUMMONER_MATCH_INFO)
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "summoner_tag_name", description = "조회할 소환사 태그네임",
            required = true, example = "김솔민-top"),
        @Parameter(in = ParameterIn.QUERY, name = "ended", description = "특정 게임 이전에 진행한 게임을 조회할 때 사용하는 ID 필터로, "
            + "해당 필드를 사용하면 소환사 정보를 제외한 매치 리스트만 넘겨짐",
            example = "KR_6841389632"),
        @Parameter(in = ParameterIn.QUERY, name = "queue_type", description = "검색할 큐 타입 정보",
            example = "ALL"),
    })
    public CommonResponse<SummonerMatchRes> getSummonerMatchInfo(
        @PathVariable("summoner_tag_name") String summonerTagName,
        @RequestParam("ended") Optional<String> lastMatchId,
        @RequestParam(value = "queue_type", defaultValue = "ALL") QueueType queueType) {

        String internalTagName = keywordToInternalTagName(summonerTagName, true);

        SummonerMatchRes result;

        if (lastMatchId.isEmpty() || lastMatchId.get().isBlank()) {
            result = summonerService.getSummonerMatchInfoBySummonerName(internalTagName, queueType);

        } else {
            result = SummonerMatchRes.builder()
                .matches(summonerService.get20MatchesByOptionalLastMatchId(internalTagName, lastMatchId.get(), queueType))
                .build();
        }

        return CommonResponse.success(result);
    }

    @GetMapping("/matches/id/{match_id}")
    @Operation(summary = ApiSummary.GET_MATCH_DETAIL, description = ApiDescription.GET_MATCH_DETAIL)
    @Parameter(in = ParameterIn.PATH, name = "match_id", description = "조회할 전적 ID",
        required = true, example = "KR_6841389632")
    public CommonResponse<MatchDetailRes> getMatchDetail(
        @PathVariable("match_id") String matchId) {
        return CommonResponse.success(summonerService.getMatchDetail(matchId));
    }


    @GetMapping("/champion/{summoner_tag_name}")
    @Operation(summary = ApiSummary.GET_CHAMPION_PLAYS, description = ApiDescription.GET_CHAMPION_PLAYS)
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "summoner_tag_name", description = "조회할 소환사 태그네임",
            required = true, example = "김솔민-top"),
        @Parameter(in = ParameterIn.QUERY, name = "brief", description = "true로 지정하면 최대 10개의 챔피언 플레이 정보 호출",
            example = "true"),
        @Parameter(in = ParameterIn.QUERY, name = "queue_type", description = "검색할 큐 타입 정보",
            example = "ALL"),
    })
    public CommonResponse<SummonerPlayRes> getChampionPlays(
        @PathVariable("summoner_tag_name") String summonerTagName,
        @RequestParam(value = "queue_type", defaultValue = "ALL") QueueType queueType,
        @RequestParam(value = "brief", defaultValue = "false") Boolean brief) {

        String internalTagName = keywordToInternalTagName(summonerTagName, true);

        List<QueueType> available = new ArrayList<>(Arrays.asList(QueueType.ALL, QueueType.RANK_SOLO, QueueType.RANK_FLEX));

        if (!available.contains(queueType) ){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "queue_type은 ALL, RANK_SOLO, RANK_FLEX 중 하나여야 합니다.");
        }

        List<SummonerPlayDto> results = summonerService.getChampionPlayInfo(
            internalTagName, queueType, brief);

        return CommonResponse.success(SummonerPlayRes.builder()
            .summonerPlays(results)
            .build());
    }

    @PostMapping("/{puuid}")
    @Operation(summary = ApiSummary.REFRESH_SUMMONER, description = ApiDescription.REFRESH_SUMMONER)
    @Parameter(in = ParameterIn.PATH, name = "puuid", description = "갱신할 소환사 PUUID",
        required = true, example = "rVQ34xrycFeCv2kU8Vjt4rjyaNTGFvaV84Rk5kPvP0-MBg0z8IeQ0UNy6dW0uANYY03hLi5H06AbCQ")
    public CommonResponse refreshSummoner(@PathVariable("puuid") String puuid) {
        summonerService.refreshSummoner(puuid);

        return CommonResponse.success("소환사 정보를 성공적으로 갱신했습니다.", HttpStatus.OK);
    }
    @GetMapping("/ingame/{summoner_tag_name}")
    @Operation(summary = ApiSummary.GET_CURRENT_GAME, description = ApiDescription.GET_CURRENT_GAME)
    @Parameter(in = ParameterIn.PATH, name = "summoner_tag_name", description = "조회할 소환사 태그네임",
        required = true, example = "김솔민-top")
    public CommonResponse<CurrentGameRes> getCurrentGame(@PathVariable("summoner_tag_name") String summonerTagName) {

        String internalTagName = keywordToInternalTagName(summonerTagName, true);
        return CommonResponse.success(summonerService.getCurrentGame(internalTagName));
    }


    @GetMapping("/together/{summoner_tag_name}")
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "summoner_tag_name", description = "조회할 소환사 태그네임",
            required = true, example = "김솔민-top"),
        @Parameter(in = ParameterIn.QUERY, name = "queue_type", description = "검색할 큐 타입 정보",
            example = "ALL"),
    })
    public CommonResponse<RecentMemberRes> getRecentSummoners(
        @PathVariable("summoner_tag_name") String summonerTagName,
        @RequestParam(value = "queue_type", defaultValue = "RANK_SOLO") QueueType queueType) {

        List<QueueType> available = new ArrayList<>(Arrays.asList(QueueType.RANK_SOLO, QueueType.RANK_FLEX));

        String internalTagName = keywordToInternalTagName(summonerTagName, true);

        if (!available.contains(queueType) ){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "queue_type은 RANK_SOLO, RANK_FLEX 중 하나여야 합니다.");
        }

        List<RecentMemberDto> results = summonerService.getRecentMemberInfo(internalTagName, queueType);

        return CommonResponse.success(RecentMemberRes.builder()
            .recentMembers(results)
            .build());
    }


}
