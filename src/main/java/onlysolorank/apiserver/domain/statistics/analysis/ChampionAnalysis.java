package onlysolorank.apiserver.domain.statistics.analysis;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.Position;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBuildComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemStartComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SkillComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SpellComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.StatPerkComponentStat;
import org.springframework.data.mongodb.core.mapping.Document;

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

//    private List<PerkComponentStat> perks;

    private List<SkillComponentStat> skillTree;
    private List<ItemStartComponentStat> itemStart;
    //    private List<ItemBootsComponentStat> itemBoots;
    private List<ItemBuildComponentStat> itemBuild;

    public ChampionAnalysis(String tier, Long championId, Position position, Double winRate,
        Double pickRate, Double banRate, Long plays, Double score, List<StatPerkComponentStat> statPerks,
        List<SpellComponentStat> summonerSpell, List<SkillComponentStat> skillTree,
        List<ItemStartComponentStat> itemStart, List<ItemBuildComponentStat> itemBuild) {

        super(tier, championId, winRate, pickRate, plays, score, position, banRate );
        this.statPerks = statPerks;
        this.summonerSpell = summonerSpell;
        this.skillTree = skillTree;
        this.itemStart = itemStart;
        this.itemBuild = itemBuild;
    }

    //    @Field("gameVersion_")
    //    private String version;
}
