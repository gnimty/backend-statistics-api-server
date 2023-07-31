package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.dto.SummonerDto;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
 */
@RestController
@RequestMapping("/summoners")
@RequiredArgsConstructor
@Slf4j
@Validated
public class SummonerController {
    private final SummonerService summonerService;
    @GetMapping("/autocomplete")
    public ResponseEntity getSummonerByInternalName(@ModelAttribute @Valid KeywordRequestDto keywordRequestDto) {
        List<SummonerDto> data = summonerService.getSummonerByInternalName(keywordRequestDto.getInternalName());
        return CommonResponse.success(data);
    }

    @GetMapping("/matches/{summoner_name}")
    public ResponseEntity getSummonerMatchInfoBySummonerName(@PathVariable("summoner_name") String summonerName) {
        SummonerMatchResponseDto data = summonerService.getSummonerMatchInfoBySummonerName(summonerName);

        return CommonResponse.success(data);
    }

}
