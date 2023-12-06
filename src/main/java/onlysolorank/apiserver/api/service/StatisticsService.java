package onlysolorank.apiserver.api.service;

import static onlysolorank.apiserver.api.exception.ErrorCode.RESULT_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.ChampionAnalysisRes;
import onlysolorank.apiserver.api.controller.dto.ChampionTierRes;
import onlysolorank.apiserver.api.controller.dto.ChampionTierRes.ChampionTierByPosition;
import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
import onlysolorank.apiserver.api.service.dto.ChampionTotalStatDto;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.ChampionCache;
import onlysolorank.apiserver.domain.dto.Position;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.counter.BaseCounter;
import onlysolorank.apiserver.domain.statistics.tier.BaseChampionTier;
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

    private final ChampionStatisticsRepositoryCustom championStatisticsRepositoryCustom;
    private final ChampionAnalysisRepository championAnalysisRepository;
    private final ChampionRepository championRepository;
    private final ChampionCounterRepository championCounterRepository;
    private final ChampionCache championCache;
    private final AssetService assetService;

    public List<ChampionTotalStatDto> getAllChampionStats(Tier tier, Period period,
        Position position) {
        List<ChampionTotalStatDto> result = championStatisticsRepositoryCustom.findStats(period,
                position, tier).stream()
            .map(championStat -> ChampionTotalStatDto.builder()
                .stat(championStat)
                .championName(championCache.resolve(championStat.getChampionId()))
                .build()).toList();
        return result;
    }

    public ChampionTierRes getChampionTierList(Boolean brief, Tier tier) {
        String version = assetService.getLatestVersionString();

        List<ChampionTierByPosition> results = new ArrayList<>();

        for (Position position : Position.getActualPosition()) {
            results.add(ChampionTierByPosition.builder()
                .position(position)
                .champions(championStatisticsRepositoryCustom.findTier(position, brief).stream()
                    .map(b -> ChampionTierDto.builder()
                        .stat(b)
                        .championName(championCache.resolve(b.getChampionId()))
                        .build()
                    ).toList())
                .build());
        }

        return ChampionTierRes.builder()
            .version(version)
            .results(results)
            .build();
    }

    public ChampionAnalysisRes getChampionAnalysis(String championName, Position position,
        Tier tier) {
        Champion champion = championRepository.findOneByEnName(championName).orElseThrow(
            () -> new CustomException(RESULT_NOT_FOUND, "champion 이름에 해당하는 챔피언 정보가 없습니다."));

        Optional<ChampionAnalysis> optionalAnalysis;

        // 1. position==UNKNOWN일 경우 해당 티어대에서 가장 많이 플레이한 position 정보로 변경하기
        // db.getCollection("champion_statistics_detail").find({"championId":517, "tier":"EMERALD"}).sort({"gameVersion_":-1, "pickRate":-1}).limit(1)
        if (position == Position.UNKNOWN) {
            optionalAnalysis = championAnalysisRepository.findTop1ByChampionIdAndTierOrderByVersionDescPickRateDesc(
                champion.getChampionId(), tier);
        }

        // 2. position이 지정되어 있다면 해당 정보들로 바로 쿼리
        // db.getCollection("champion_statistics_detail").find({"championId":517, "tier":"EMERALD", "teamPosition":"JUNGLE"}).sort({"gameVersion_":-1}).limit(1)
        else {
            optionalAnalysis = championAnalysisRepository.findTop1ByChampionIdAndPositionAndTierOrderByVersionDesc(
                champion.getChampionId(), Position.valueOf(position.name()), tier);
        }

        if (!optionalAnalysis.isPresent()) {
            throw new CustomException(RESULT_NOT_FOUND,
                String.format("%s에 해당하는 챔피언 분석 정보가 존재하지 않습니다.", position.getValue()));
        }

        // 3. 추적한 position 정보로 counter champion, easy champion 얻기
        ChampionAnalysis analysis = optionalAnalysis.get();

        List<BaseCounter> counterChampions = championCounterRepository.findCounterChampions(
            champion.getChampionId(), analysis.getPosition(), true);
        List<BaseCounter> easyChampions = championCounterRepository.findCounterChampions(
            champion.getChampionId(), analysis.getPosition(), false);

        BaseChampionTier championTier = championStatisticsRepositoryCustom.findTier(position,
                champion.getChampionId())
            .orElseGet(null);

        if (championTier == null) {
            return ChampionAnalysisRes.toRes(analysis, counterChampions, easyChampions, null);
        } else {
            ChampionTierDto championTierDto = ChampionTierDto.builder()
                .stat(championTier)
                .championName(championName)
                .build();

            return ChampionAnalysisRes.toRes(analysis, counterChampions, easyChampions,
                championTierDto);
        }
    }

}
