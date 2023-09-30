package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.api.service.dto.ChampionStatBriefDto;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepository;
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepositoryCustom;
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepositoryV2;
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
 * 2023/09/30        solmin       전체 챔피언 통계정보, 포지션별 챔피언 티어정보 개발
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final ChampionStatisticsRepository championStatisticsRepository;
    private final ChampionStatisticsRepositoryV2 championStatisticsRepositoryV2;
    private final ChampionStatisticsRepositoryCustom championStatisticsRepositoryCustom;

    public List<ChampionStatBriefDto> getAllChampionStats(TierFilter tier, Period period, PositionFilter position) {

        List<ChampionStatBriefDto> result =
            championStatisticsRepositoryCustom.findStats(period, position, tier)
                .stream()
                .map(ChampionStatBriefDto::new)
                .toList();

        // position = all 이면 positionfilter 없애기
        // 그렇지 않으면 position에 따른 통계 정보만 보여주기

        return result;
    }

    public List<ChampionTierDto> getChampionDetail(PositionFilter position) {

        List<ChampionTierDto> result = championStatisticsRepositoryCustom.findTierStats(position)
            .stream()
            .map(ChampionTierDto::new)
            .toList();


        return result;
    }
}
