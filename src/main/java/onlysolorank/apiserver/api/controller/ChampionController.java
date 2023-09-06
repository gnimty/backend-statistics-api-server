package onlysolorank.apiserver.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.StatisticsService;
import onlysolorank.apiserver.api.service.dto.ChampionStatsDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : ChampionController
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/champion")
public class ChampionController {

    private final StatisticsService statisticsService;

    @GetMapping("/stats/total")
    public List<ChampionStatsDto> getAllChampionStats(){

        return statisticsService.getAllChampionStats();
    }


}
