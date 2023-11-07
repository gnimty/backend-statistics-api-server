package onlysolorank.apiserver.repository.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
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
import org.jetbrains.annotations.Nullable;
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
    public List<? extends BaseChampionTier> findTier(PositionFilter position, Boolean brief) {

        Criteria criteria = new Criteria();

        Query query = new Query(criteria)
            .with(Sort.by(Sort.Order.desc("score")));

        if(brief){
            query.limit(BRIEF_CNT);
        }

        List<? extends BaseChampionTier> result = new ArrayList<>();

        Class targetPositionClass = getTargetPositionClass(position).getTargetClass();

        result = mongoTemplate.find(query, targetPositionClass);

        return result;
    }

    @Override
    public Optional<? extends BaseChampionTier> findTier(PositionFilter position, Long championId){
        TargetPositionClass tpc = getTargetPositionClass(position);

        Query query = new Query(Criteria.where(tpc.getTargetField()).is(championId));

        return Optional.of((BaseChampionTier) mongoTemplate.find(query, tpc.getTargetClass()));
    }

    private static TargetPositionClass getTargetPositionClass(PositionFilter position) {


        switch (position) {
            case TOP:
                return new TargetPositionClass(TopTier.class, "TOP");
            case JUNGLE:
                return new TargetPositionClass(JungleTier.class, "JUG");
            case MIDDLE:
                return new TargetPositionClass(MiddleTier.class, "MID");
            case BOTTOM:
                return new TargetPositionClass(BottomTier.class, "ADC");
            case UTILITY:
                return new TargetPositionClass(UtilityTier.class, "SUP");
        }
        return null;
    }


    @Data
    @AllArgsConstructor
    private static class TargetPositionClass{
        private Class targetClass;
        private String targetField;
    }

}
