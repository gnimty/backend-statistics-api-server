package onlysolorank.apiserver.repository.summoner_play;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.summoner_play.BaseSummonerPlay;
import onlysolorank.apiserver.domain.summoner_play.SummonerPlay;
import onlysolorank.apiserver.domain.summoner_play.SummonerPlayFlex;
import onlysolorank.apiserver.domain.summoner_play.SummonerPlayTotal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner_play
 * fileName       : SummonerPlayRepositoryImpl
 * author         : solmin
 * date           : 2023/09/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/05        solmin       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class SummonerPlayRepositoryImpl implements SummonerPlayRepositoryCustom {

    @Qualifier("primaryMongoTemplate")
    private final MongoTemplate mongoTemplate;

    private final int STD_PLAYS = 50;
    private final int STD_MMR = 2600;

    @Override
    public List<SummonerPlay> findSpecialists(Long championId) {
        Query query = new Query(Criteria.where("championId").is(championId)
            .and("totalPlays").gte(STD_PLAYS)
            .and("mmr").gte(STD_MMR))
            .with(Sort.by(Sort.Order.desc("totalPlays")))
            .limit(5);

        return mongoTemplate.find(query, SummonerPlay.class);
    }

    @Override
    public List<? extends BaseSummonerPlay> findByPuuidChampionIdPairs(Map<String, Long> pairs, QueueType queueType, String season) {
        List<Criteria> criteriaList = pairs.entrySet().stream()
            .map(pair -> {
                String puuid = pair.getKey();
                Long championId = pair.getValue();
                return Criteria.where("puuid").is(puuid)
                    .and("championId").is(championId)
                    .and("season").is(season);
            })
            .toList();

        Query query = new Query(new Criteria().orOperator(criteriaList));

        return findByQueueType(query, queueType);
    }


    public List<? extends BaseSummonerPlay> findAllByQueueType(String puuid, String season, QueueType queueType){
        Query query = new Query(Criteria.where("puuid").is(puuid)
            .and("season").is(season))
            .with(Sort.by(Sort.Order.desc("totalPlays")))
            .with(Sort.by(Sort.Order.desc("winRate")))
            .with(Sort.by(Sort.Order.desc("avgKda")));

        return findByQueueType(query, queueType);
    }

    public List<? extends BaseSummonerPlay> findAllByQueueType(String puuid, String season, QueueType queueType, Integer limit){
        Query query = new Query(Criteria.where("puuid").is(puuid)
            .and("season").is(season))
            .with(Sort.by(Sort.Order.desc("totalPlays")))
            .with(Sort.by(Sort.Order.desc("winRate")))
            .with(Sort.by(Sort.Order.desc("avgKda")))
            .limit(limit);

        return findByQueueType(query, queueType);
    }

    @Data
    public static class ChampionIdResult {
        private String puuid;
        private List<Long> championIds;
    }

    List<? extends BaseSummonerPlay> findByQueueType(Query query, QueueType queueType){
        switch (queueType) {
            case RANK_SOLO:
                return mongoTemplate.find(query, SummonerPlay.class);
            case RANK_FLEX:
                return mongoTemplate.find(query, SummonerPlayFlex.class);
            case ALL:
                return mongoTemplate.find(query, SummonerPlayTotal.class);
        }
        return null;
    }
}
