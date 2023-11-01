package onlysolorank.apiserver.domain.statistics.analysis;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBootsComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemRarityComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemStartComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.PerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SkillComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SpellComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.StatPerkComponentStat;
import org.springframework.data.annotation.Id;
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

@Document("detail")
@AllArgsConstructor
@Getter
@ToString
public class ChampionAnalysis {

    @Id
    private String id;

    private Long championId;

    @Field("teamPosition")
    private PositionFilter position;

    @Field("gameVersion_")
    private String version;

    private TierFilter tier;

    private Double winRate;
    private Double pickRate;
    private Double banRate;

    @Field("total")
    private Long plays;

    private List<StatPerkComponentStat> statPerks;
    private List<PerkComponentStat> perks;

    private List<SpellComponentStat> summonerSpell;

    private List<SkillComponentStat> skillTree;
    private List<ItemStartComponentStat> itemStart;
    private List<ItemBootsComponentStat> itemBoots;
    private List<ItemRarityComponentStat> itemRarity;
}
