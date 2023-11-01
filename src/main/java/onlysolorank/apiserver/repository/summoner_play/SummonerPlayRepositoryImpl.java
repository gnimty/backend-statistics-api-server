package onlysolorank.apiserver.repository.summoner_play;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.api.service.dto.PuuidChampionIdPair;
import onlysolorank.apiserver.domain.SummonerPlay;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
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

    @Override
    public List<SummonerPlay> findSpecialists(String championName, int totalPlays) {
        Query query = new Query(Criteria.where("championName").is(championName)
            .and("totalPlays").gte(totalPlays))
            .with(Sort.by(Sort.Order.desc("totalPlays")))
            .limit(100);

        return mongoTemplate.find(query, SummonerPlay.class);
    }

    @Override
    public List<SummonerPlay> findByPuuidChampionIdPairs(List<PuuidChampionIdPair> pairs) {
        List<Criteria> criteriaList = pairs.stream().map(p ->
            Criteria.where("puuid").is(p.getPuuid()).and("championId").is(p.getChampionId())
        ).toList();

        Query query = new Query(new Criteria().orOperator(criteriaList));

        return mongoTemplate.find(query, SummonerPlay.class);
    }

    @Override
    public List<SummonerPlay> findSummonerPlaysByPuuidAndLimit(String puuid, int limit) {
        Query query = new Query(Criteria.where("puuid").is(puuid))
            .with(Sort.by(Sort.Order.desc("plays")))
            .limit(10);

        return mongoTemplate.find(query, SummonerPlay.class);
    }

    @Override
    public Map<String, List<Long>> findMostPlayedChampionsByPuuidsAndLimit(List<String> puuids,
        int limit) {

        MatchOperation matchOperation = Aggregation.match(Criteria.where("puuid").in(puuids));

        // Sort stage: totalPlays 필드를 내림차순으로 정렬합니다.
        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Order.desc("totalPlays")));

        // Group stage: puuid로 그룹화하고 챔피언 ID를 배열로 수집합니다.
        GroupOperation groupOperation = Aggregation.group("puuid")
            .push("championId").as("championIds");

        // Limit stage: 각 그룹에서 제한값 만큼의 챔피언 ID만 선택합니다.
        LimitOperation limitOperation = Aggregation.limit(limit);

        // Aggregation pipeline 정의
        Aggregation aggregation = Aggregation.newAggregation(
            matchOperation,
            sortOperation,
            groupOperation,
            limitOperation
        );

        AggregationResults<ChampionIdResult> results = mongoTemplate.aggregate(aggregation,
            "summoner_plays", ChampionIdResult.class);

        // 결과를 Map으로 변환
        Map<String, List<Long>> resultMap = results.getMappedResults()
            .stream()
            .collect(
                Collectors.toMap(ChampionIdResult::getPuuid, ChampionIdResult::getChampionIds));

        return resultMap;
    }

    @Data
    public static class ChampionIdResult {

        private String puuid;
        private List<Long> championIds;
    }
}
