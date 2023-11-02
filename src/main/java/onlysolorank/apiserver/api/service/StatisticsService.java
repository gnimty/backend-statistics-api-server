package onlysolorank.apiserver.api.service;

import static onlysolorank.apiserver.api.exception.ErrorCode.RESULT_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.ChampionAnalysisRes;
import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.service.dto.ChampionStatBriefDto;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.ChampionCache;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.counter.BaseCounter;
import onlysolorank.apiserver.repository.analysis.ChampionAnalysisRepository;
import onlysolorank.apiserver.repository.champion.ChampionRepository;
import onlysolorank.apiserver.repository.counter.ChampionCounterRepository;
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepositoryCustom;
import org.springframework.stereotype.Service;

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

//    private final ChampionStatisticsRepository championStatisticsRepository;
//    private final ChampionStatisticsRepositoryV2 championStatisticsRepositoryV2;
    private final ChampionStatisticsRepositoryCustom championStatisticsRepositoryCustom;
    private final ChampionAnalysisRepository championAnalysisRepository;
    private final ChampionRepository championRepository;
    private final ChampionCounterRepository championCounterRepository;
    private final ChampionCache championCache;

    public List<ChampionStatBriefDto> getAllChampionStats(TierFilter tier, Period period,
        PositionFilter position) {

        List<ChampionStatBriefDto> result = championStatisticsRepositoryCustom.findStats(period,
            position, tier).stream().map(ChampionStatBriefDto::new).toList();

        // position = all 이면 positionfilter 없애기
        // 그렇지 않으면 position에 따른 통계 정보만 보여주기

        return result;
    }

    public List<ChampionTierDto> getChampionTierList(PositionFilter position, Boolean brief) {

        List<ChampionTierDto> result =
            championStatisticsRepositoryCustom.findTierStats(position, brief).stream()
                .map(b -> ChampionTierDto.builder()
                    .stat(b)
                    .championName(championCache.resolve(b.getChampionId()))
                    .build()
                ).toList();

        return result;
    }

    public ChampionAnalysisRes getChampionAnalysis(String championName, PositionFilter position,
        TierFilter tier) {
        Champion champion = championRepository.findOneByEnName(championName).orElseThrow(
            () -> new CustomException(RESULT_NOT_FOUND, "champion 이름에 해당하는 챔피언 정보가 없습니다."));

        Optional<ChampionAnalysis> optionalAnalysis;
        // 1. position==UNKNOWN일 경우 해당 티어대에서 가장 많이 플레이한 position 정보로 변경하기
        // db.getCollection("champion_statistics_detail").find({"championId":517, "tier":"EMERALD"}).sort({"gameVersion_":-1, "pickRate":-1}).limit(1)
        if (position == PositionFilter.UNKNOWN) {
            optionalAnalysis = championAnalysisRepository.findTop1ByChampionIdAndTierOrderByVersionDescPickRateDesc(
                champion.getChampionId(), tier);
        }
        // 2. position이 지정되어 있다면 해당 정보들로 바로 쿼리
        // db.getCollection("champion_statistics_detail").find({"championId":517, "tier":"EMERALD", "teamPosition":"JUNGLE"}).sort({"gameVersion_":-1}).limit(1)
        else {
            optionalAnalysis = championAnalysisRepository.findTop1ByChampionIdAndPositionAndTierOrderByVersionDesc(
                champion.getChampionId(), PositionFilter.valueOf(position.name()), tier);
        }

        if(!optionalAnalysis.isPresent()){
            throw new CustomException(RESULT_NOT_FOUND, String.format("%s에 해당하는 챔피언 분석 정보가 존재하지 않습니다.", position.getValue()));
        }

        // 3. 추적한 position 정보로 counter champion, easy champion 얻기

        ChampionAnalysis analysis = optionalAnalysis.get();

        List<BaseCounter> counterChampions = championCounterRepository.findCounterChampions(
            champion.getChampionId(), analysis.getPosition(), true);
        List<BaseCounter> easyChampions = championCounterRepository.findCounterChampions(
            champion.getChampionId(), analysis.getPosition(), false);

        return ChampionAnalysisRes.toRes(analysis, counterChampions, easyChampions);
    }

//    private PositionFilter replaceToPopularPosition(PositionFilter position){
//        if(position==PositionFilter.UNKNOWN){
//            // TODO
//        }
//        return position;
//    }

}
