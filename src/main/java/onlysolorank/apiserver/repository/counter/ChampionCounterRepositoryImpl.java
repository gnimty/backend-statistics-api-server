package onlysolorank.apiserver.repository.counter;

import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.domain.statistics.analysis.counter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.counter
 * fileName       : ChampionCounterRepositoryImpl
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */
@Repository
public class ChampionCounterRepositoryImpl implements ChampionCounterRepositoryCustom{
    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public List<BaseCounter> findCounterChampions(Long championId, PositionFilter position, boolean isCounter) {

        Query query;

        if(isCounter){
            query = new Query(Criteria.where("championId").is(championId))
                .with(Sort.by(Sort.Order.asc("winRate")))
                .limit(3);
        }else{
            query = new Query(Criteria.where("opponentChampionId").is(championId))
                .with(Sort.by(Sort.Order.desc("winRate")))
                .limit(3);
        }

        List<? extends BaseCounter> result = new ArrayList<>();

        switch(position){
            case TOP:
                result =  mongoTemplate.find(query, TopCounter.class);
                break;
            case JUNGLE:
                result =  mongoTemplate.find(query, JungleCounter.class);
                break;
            case MIDDLE:
                result =  mongoTemplate.find(query, MiddleCounter.class);
                break;
            case BOTTOM:
                result =  mongoTemplate.find(query, BottomCounter.class);
                break;
            case UTILITY:
                result =  mongoTemplate.find(query, UtilityCounter.class);
                break;
        }

        return result.stream()
            .map(r-> (BaseCounter) r)
            .toList();
    }
}
