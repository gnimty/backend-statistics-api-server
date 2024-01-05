package onlysolorank.apiserver.api.service;

import static onlysolorank.apiserver.api.exception.ErrorCode.RESULT_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.controller.dto.ChampionAnalysisRes;
import onlysolorank.apiserver.api.controller.dto.ChampionAramTierRes;
import onlysolorank.apiserver.api.controller.dto.ChampionTierRes;
import onlysolorank.apiserver.api.controller.dto.ChampionTierRes.ChampionTierByPosition;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.ChampionCache;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionPatch;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionStatsRank;
import onlysolorank.apiserver.repository.analysis.ChampionAnalysisRepository;
import onlysolorank.apiserver.repository.champion.ChampionRepository;
import onlysolorank.apiserver.repository.patch.ChampionPatchRepository;
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

    private final ChampionAnalysisRepository championAnalysisRepository;
    private final ChampionRepository championRepository;
    private final ChampionCache championCache;
    private final AssetService assetService;
    private final ChampionPatchRepository championPatchRepository;

    private static final QueueType QUEUE_TYPE_ON_DETAIL = QueueType.RANK_SOLO;

    public Map<Long, String> getChampionMap() {
        return championCache.enMap;
    }

    public ChampionTierRes getChampionTierList(QueueType queueType, Boolean brief, Tier tier) {
        String version = assetService.getLatestVersionString();

        Map<Long, String> championEnMap = getChampionMap();

        List<ChampionTierByPosition> results = new ArrayList<>();

        for (Lane position : Lane.getActualPosition()) {

            List<ChampionStatsRank> championTierList = championAnalysisRepository.findChampionTierList(
                queueType, position, brief, tier.name().toUpperCase());

            List<ChampionTierDto> championTierDtoList = championTierList.stream().map(
                        championTier -> ChampionTierDto.fromRankTier(championTier,
                            championEnMap.getOrDefault(championTier.getChampionId(), null)))
                    .toList();

            results.add(ChampionTierByPosition.builder()
                .position(position)
                .champions(championTierDtoList)
                .build());
        }

        return ChampionTierRes.builder()
            .version(version)
            .results(results)
            .build();
    }


    public ChampionAramTierRes getChampionAramTierList(QueueType queueType, Boolean brief, Tier tier) {
        String version = assetService.getLatestVersionString();

        Map<Long, String> championEnMap = getChampionMap();

        List<ChampionTierDto> results = championAnalysisRepository.findChampionAramTierList(
            queueType, brief, tier.name().toUpperCase()).stream().map(
                championTier -> ChampionTierDto.fromBaseTier(championTier,
                    championEnMap.getOrDefault(championTier.getChampionId(), null)))
            .toList();

        return ChampionAramTierRes.builder()
            .version(version)
            .results(results)
            .build();
    }


    public ChampionAnalysisRes getChampionAnalysis(String championName, Lane lane, Tier tier) {

        Champion champion = championRepository.findOneByEnName(championName).orElseThrow(
            () -> new CustomException(RESULT_NOT_FOUND, "champion 이름에 해당하는 챔피언 정보가 없습니다."));

        Optional<ChampionAnalysis> optionalAnalysis;

        // 1. lane==UNKNOWN일 경우 해당 티어대에서 가장 많이 플레이한 lane 정보로 변경하기
        // db.getCollection("champion_statistics_detail").find({"championId":517, "tier":"EMERALD"}).sort({"gameVersion_":-1, "pickRate":-1}).limit(1)
        if (lane == Lane.UNKNOWN) {
            optionalAnalysis = championAnalysisRepository.findTop1ByChampionIdAndTier(QUEUE_TYPE_ON_DETAIL,
                champion.getChampionId(), tier.name().toUpperCase());
        }

        // 2. position이 지정되어 있다면 해당 정보들로 바로 쿼리
        else {
            optionalAnalysis = championAnalysisRepository.findTop1ByChampionIdAndPositionAndTier(QUEUE_TYPE_ON_DETAIL,
                champion.getChampionId(), Lane.valueOf(lane.name()), tier.name().toUpperCase());
        }

        if (!optionalAnalysis.isPresent()) {
            throw new CustomException(RESULT_NOT_FOUND,
                String.format("%s에 해당하는 챔피언 분석 정보가 존재하지 않습니다.", lane.getValue()));
        }

        // 3. 추적한 lane 정보로 counter champion, easy champion 얻기
        ChampionAnalysis analysis = optionalAnalysis.get();

        String version = assetService.getLatestVersionString();
        List<ChampionPatch> patches = championPatchRepository.findByVersionLessThanEqualAndAndChampionId(version, analysis.getChampionId().toString());
//        List<BaseCounter> easyChampions = championCounterRepository.findCounterChampions(
//            champion.getChampionId(), analysis.getPosition(), false);

//        BaseChampionTier championTier = championStatisticsRepositoryCustom.findTier(lane,champion.getChampionId())
//            .orElseGet(null);

//        if (championTier == null) {
//            return ChampionAnalysisRes.toRes(analysis, counterChampions, easyChampions, null);
//        } else {
//        }

        ChampionTierDto championTierDto = ChampionTierDto.fromRankTier(analysis, championName);

        return ChampionAnalysisRes.toRes(analysis, championTierDto, patches);
    }

}
