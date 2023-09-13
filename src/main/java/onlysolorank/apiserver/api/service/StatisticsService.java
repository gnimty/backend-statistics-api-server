package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.service.dto.ChampionStatsDto;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.repository.champion.ChampionRepository;
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : StatisticsService
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 * 2023/09/13        solmin       전체 챔피언 및 특정 챔피언 조회
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final ChampionStatisticsRepository championStatisticsRepository;

    public List<ChampionStatsDto> getAllChampionStats() {
        List<ChampionStatsDto> result =
            championStatisticsRepository.findAllByOrderByTotalPlaysDesc().stream()
                .map(ChampionStatsDto::new).toList();

        return result;
    }
}
