package onlysolorank.apiserver.repository.analysis;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatsRank;
import onlysolorank.apiserver.domain.statistics.stat.AramStat;
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

    private String resolveTargetCollection(QueueType queueType, Tier tier) {
        String targetCol = "champion_statistics_";

        if (queueType == QueueType.ARAM) {
            targetCol += "aram";
        } else {
            if (queueType == QueueType.RANK_SOLO) {
                targetCol += "solo_";
            } else if (queueType == QueueType.RANK_FLEX) {
                targetCol += "flex_";
            }
            targetCol += tier.name().toLowerCase();
        }
        return targetCol;
    }

    @Override
    public Optional<ChampionAnalysis> findTop1ByChampionIdAndPositionAndTier(QueueType queueType, Long championId,
        Lane position, Tier upperTier) {

        Query query = new Query(Criteria.where("championId").is(championId).and("teamPosition").is(position));

        ChampionAnalysis result = mongoTemplate.findOne(query, ChampionAnalysis.class,
            resolveTargetCollection(queueType, upperTier));

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ChampionAnalysis> findTop1ByChampionIdAndTier(QueueType queueType, Long championId,
        Tier upperTier) {

        Query query = new Query(Criteria.where("championId").is(championId))
            .with(Sort.by(Sort.Order.desc("pick_cnt")));

        ChampionAnalysis result = mongoTemplate.findOne(query, ChampionAnalysis.class,
            resolveTargetCollection(queueType, upperTier));

        return Optional.ofNullable(result);
    }

    @Override
    public List<ChampionStatsRank> findChampionTierList(QueueType queueType, Lane position, Boolean brief, Tier upperTier) {
        Query query = new Query(Criteria.where("teamPosition").is(position))
            .with(Sort.by(Sort.Order.desc("score")));

        if(brief){
            query = query.limit(5);
        }

        return mongoTemplate.find(query, ChampionStatsRank.class,
                resolveTargetCollection(queueType, upperTier));
    }

    @Override
    public List<AramStat> findChampionAramTierList(QueueType queueType, Boolean brief, Tier upperTier) {
        Query query = new Query()
            .with(Sort.by(Sort.Order.desc("score")));

        if(brief){
            query = query.limit(5);
        }

        return mongoTemplate.find(query, AramStat.class, resolveTargetCollection(queueType, upperTier));
    }

    @Override
    public List<ChampionStatsRank> findChampionLaneSelectRate(Long championId, Tier tier) {
        Query query = new Query(Criteria.where("championId").is(championId)
            .and("teamPosition").ne(null))
            .with(Sort.by(Sort.Order.desc("pick_cnt")));

        return mongoTemplate.find(query, ChampionStatsRank.class,
            resolveTargetCollection(QueueType.RANK_SOLO, tier));
    }


}
