package onlysolorank.apiserver.domain.statistics.analysis;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBootsComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBuildComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemMiddleComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemStartComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.PerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SkillComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SpellComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.StatPerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SynergyStat;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatsRank;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : ChampionAnalysis
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Document
@Getter
@ToString
public class ChampionAnalysis extends ChampionStatsRank {


    private List<StatPerkComponentStat> statPerks;
    private List<SpellComponentStat> summonerSpell;

    private List<PerkComponentStat> perks;

    private List<SkillComponentStat> skillTree;
    private List<ItemStartComponentStat> itemStart;
    private List<ItemBootsComponentStat> itemBoots;
    private List<ItemBuildComponentStat> itemBuild;
    private List<ItemMiddleComponentStat> itemMiddle;


    @Field("easy")
    private List<CounterStat> easy;

    @Field("synergy")
    private List<SynergyStat> synergies;

    public ChampionAnalysis(String tier, Long championId, Double winRate, Double pickRate, Long plays, Double score,
        Lane position, Double banRate, List<CounterStat> counters) {
        super(tier, championId, winRate, pickRate, plays, score, position, banRate, counters);
    }

}
