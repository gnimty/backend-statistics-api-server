package onlysolorank.apiserver.repository.summoner_play;

import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.domain.SummonerPlay;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public class SummonerPlayRepositoryImpl implements SummonerPlayRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Override
    public List<SummonerPlay> findSpecialists(String championName, int totalPlays) {
        Query query = new Query(Criteria.where("championName").is(championName)
            .and("totalPlays").gte(totalPlays))
            .with(Sort.by(Sort.Order.desc("totalPlays")))
            .limit(100);

        return mongoTemplate.find(query, SummonerPlay.class);
    }
}
