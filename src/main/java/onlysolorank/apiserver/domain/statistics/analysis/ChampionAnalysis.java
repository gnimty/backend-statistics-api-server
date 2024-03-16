package onlysolorank.apiserver.domain.statistics.analysis;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBootsComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBuildComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemStartComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.PerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SkillComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SpellComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.StatPerkComponentStat;
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

    @Field("counter")
    private List<CounterStat> counters;

    @Field("easy")
    private List<CounterStat> easy;

    public ChampionAnalysis(String tier, Long championId, Double winRate, Double pickRate, Long plays, Double score,
        Lane position, Double banRate) {
        super(tier, championId, winRate, pickRate, plays, score, position, banRate);
    }

//    public ChampionAnalysis(String tier, Long championId, Lane position, Double winRate,
//        Double pickRate, Double banRate, Long plays, Double score, List<StatPerkComponentStat> statPerks,
//        List<PerkComponentStat> perks, List<SpellComponentStat> summonerSpell, List<SkillComponentStat> skillTree,
//        List<ItemStartComponentStat> itemStart, List<ItemBuildComponentStat> itemBuild, List<CounterStat> counters) {
//
//        super(tier, championId, winRate, pickRate, plays, score, position, banRate);
//        this.perks = perks;
//        this.statPerks = statPerks;
//        this.summonerSpell = summonerSpell;
//        this.skillTree = skillTree;
//        this.itemStart = itemStart;
//        this.itemBuild = itemBuild;
//        this.itemBoots = itemBoots;
//        this.counters = counters;
//    }

    //    @Field("gameVersion_")
    //    private String version;
}
