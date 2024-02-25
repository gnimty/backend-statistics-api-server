package onlysolorank.apiserver.repository.analysis;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.statistics.analysis.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionStatsRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * packageName    : onlysolorank.apiserver.repository.analysis
 * fileName       : ChampionAnalysisRepositoryImpl
 * author         : solmin
 * date           : 1/3/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/3/24        solmin       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class ChampionAnalysisRepositoryImpl implements ChampionAnalysisRepositoryCustom {

    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private final MongoTemplate mongoTemplate;

    private String resolveTargetCollection(QueueType queueType, String upperTier) {
        String targetCol = "champion_statistics_";

        if (queueType == QueueType.ARAM) {
            targetCol += "aram";
        } else {
            if (queueType == QueueType.RANK_SOLO) {
                targetCol += "solo_";
            } else if (queueType == QueueType.RANK_FLEX) {
                targetCol += "flex_";
            }
            targetCol += upperTier.toLowerCase();
        }
        return targetCol;
    }

    @Override
    public Optional<ChampionAnalysis> findTop1ByChampionIdAndPositionAndTier(QueueType queueType, Long championId,
        Lane position, String upperTier) {

        Query query = new Query(Criteria.where("championId").is(championId).and("teamPosition").is(position));

        ChampionAnalysis result = mongoTemplate.findOne(query, ChampionAnalysis.class,
            resolveTargetCollection(queueType, upperTier));

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ChampionAnalysis> findTop1ByChampionIdAndTier(QueueType queueType, Long championId,
        String upperTier) {

        Query query = new Query(Criteria.where("championId").is(championId))
            .with(Sort.by(Sort.Order.desc("pick_cnt")));

        ChampionAnalysis result = mongoTemplate.findOne(query, ChampionAnalysis.class,
            resolveTargetCollection(queueType, upperTier));

        return Optional.ofNullable(result);
    }

    @Override
    public List<ChampionStatsRank> findChampionTierList(QueueType queueType, Lane position, Boolean brief, String upperTier) {
        Query query = new Query(Criteria.where("teamPosition").is(position))
            .with(Sort.by(Sort.Order.desc("score")));

        if(brief){
            query = query.limit(5);
        }

        return mongoTemplate.find(query, ChampionStatsRank.class,
                resolveTargetCollection(queueType, upperTier));
    }

    @Override
    public List<BaseChampionStat> findChampionAramTierList(QueueType queueType, Boolean brief, String upperTier) {
        Query query = new Query()
            .with(Sort.by(Sort.Order.desc("score")));

        if(brief){
            query = query.limit(5);
        }

        return mongoTemplate.find(query, BaseChampionStat.class, resolveTargetCollection(queueType, upperTier));
    }


}
