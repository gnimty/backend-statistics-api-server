package onlysolorank.apiserver.repository.statistics;

import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.domain.statistics.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.DailyChampionStat;
import onlysolorank.apiserver.domain.statistics.MonthlyChampionStat;
import onlysolorank.apiserver.domain.statistics.WeeklyChampionStat;
import onlysolorank.apiserver.domain.statistics.tier.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.statistics
 * fileName       : ChampionStatisticsRepositoryImpl
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class ChampionStatisticsRepositoryImpl implements ChampionStatisticsRepositoryCustom{

    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private final MongoTemplate secondaryMongoTemplate;

    @Override
    public List<? extends BaseChampionStat> findStats(Period period, PositionFilter position, TierFilter tier) {

        Criteria criteria = Criteria.where("pick_cnt").ne(null).and("tier").is(tier);
        if (position!=PositionFilter.ALL){
            criteria = criteria.and("teamPosition").is(position);
        }

        Query query = new Query(criteria)
            .with(Sort.by(Sort.Order.desc("pick_cnt")));

        List<? extends BaseChampionStat> result = new ArrayList<>();

        switch(period){
            case DAY:
                result = secondaryMongoTemplate.find(query, DailyChampionStat.class);
                break;
            case WEEK:
                result = secondaryMongoTemplate.find(query, WeeklyChampionStat.class);
                break;
            case MONTH:
                result = secondaryMongoTemplate.find(query, MonthlyChampionStat.class);
                break;
        }

        return result;
    }

    @Override
    public List<? extends BaseChampionTier> findTierStats(PositionFilter position) {

        Criteria criteria = new Criteria();

        Query query = new Query(criteria)
            .with(Sort.by(Sort.Order.desc("score")));

        List<? extends BaseChampionTier> result = new ArrayList<>();

        switch(position){
            case TOP:
                result =  secondaryMongoTemplate.find(query, TopTier.class);
                break;
            case JUNGLE:
                result =  secondaryMongoTemplate.find(query, JungleTier.class);
                break;
            case MIDDLE:
                result =  secondaryMongoTemplate.find(query, MiddleTier.class);
                break;
            case BOTTOM:
                result =  secondaryMongoTemplate.find(query, BottomTier.class);
                break;
            case UTILITY:
                result =  secondaryMongoTemplate.find(query, UtilityTier.class);
                break;
        }

        return result;
    }
}
