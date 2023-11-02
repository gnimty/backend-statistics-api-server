package onlysolorank.apiserver.repository.statistics;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.domain.statistics.stat.BaseChampionStat;
import onlysolorank.apiserver.domain.statistics.stat.DailyChampionStat;
import onlysolorank.apiserver.domain.statistics.stat.MonthlyChampionStat;
import onlysolorank.apiserver.domain.statistics.stat.WeeklyChampionStat;
import onlysolorank.apiserver.domain.statistics.tier.BaseChampionTier;
import onlysolorank.apiserver.domain.statistics.tier.BottomTier;
import onlysolorank.apiserver.domain.statistics.tier.JungleTier;
import onlysolorank.apiserver.domain.statistics.tier.MiddleTier;
import onlysolorank.apiserver.domain.statistics.tier.TopTier;
import onlysolorank.apiserver.domain.statistics.tier.UtilityTier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
public class ChampionStatisticsRepositoryImpl implements ChampionStatisticsRepositoryCustom {

    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private final MongoTemplate mongoTemplate;

    private final int BRIEF_CNT = 5;

    @Override
    public List<? extends BaseChampionStat> findStats(Period period, PositionFilter position,
        TierFilter tier) {

        Criteria criteria = Criteria.where("pick_cnt").ne(null).and("tier").is(tier);
        if (position != PositionFilter.ALL) {
            criteria = criteria.and("teamPosition").is(position);
        }

        Query query = new Query(criteria)
            .with(Sort.by(Sort.Order.desc("pick_cnt")));

        List<? extends BaseChampionStat> result = new ArrayList<>();

        switch (period) {
            case DAY:
                result = mongoTemplate.find(query, DailyChampionStat.class);
                break;
            case WEEK:
                result = mongoTemplate.find(query, WeeklyChampionStat.class);
                break;
            case MONTH:
                result = mongoTemplate.find(query, MonthlyChampionStat.class);
                break;
        }

        return result;
    }

    @Override
    public List<? extends BaseChampionTier> findTierStats(PositionFilter position, Boolean brief) {

        Criteria criteria = new Criteria();

        Query query = new Query(criteria)
            .with(Sort.by(Sort.Order.desc("score")));

        if(brief){
            query.limit(BRIEF_CNT);
        }

        List<? extends BaseChampionTier> result = new ArrayList<>();

        switch (position) {
            case TOP:
                result = mongoTemplate.find(query, TopTier.class);
                break;
            case JUNGLE:
                result = mongoTemplate.find(query, JungleTier.class);
                break;
            case MIDDLE:
                result = mongoTemplate.find(query, MiddleTier.class);
                break;
            case BOTTOM:
                result = mongoTemplate.find(query, BottomTier.class);
                break;
            case UTILITY:
                result = mongoTemplate.find(query, UtilityTier.class);
                break;
        }

        return result;
    }
}
