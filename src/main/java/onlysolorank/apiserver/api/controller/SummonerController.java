package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.api.dto.SummonerDto;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import onlysolorank.apiserver.api.service.SummonerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
 */
@RestController
@RequestMapping("/summoners")
@RequiredArgsConstructor
public class SummonerController {
    private final SummonerService summonerService;

    @GetMapping("/autocomplete")
    public ResponseEntity test(@RequestParam("keyword") String keyword){
        List<SummonerDto> data = summonerService.getSummonerByInternalName(keyword);
        return CommonResponse.success(data);
    }

}
