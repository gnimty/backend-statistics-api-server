package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBootsComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemRarityComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemStartComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.PerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SkillComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SpellComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.StatPerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.counter.BaseCounter;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : ChampionAnalysisRes
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@Data
@AllArgsConstructor
@Builder
public class ChampionAnalysisRes {

    private List<BaseCounter> counterChampions;
    private List<BaseCounter> easyChampions;

    private List<SpellComponentStat> spellBuilds;

    private List<PerkComponentStat> perkBuilds;
    private List<StatPerkComponentStat> statPerkBuilds;

    private List<ItemStartComponentStat> initialItemBuilds;
    private List<ItemBootsComponentStat> shoesBuilds;
    private List<ItemRarityComponentStat> itemBuilds;
    private List<SkillComponentStat> skillBuilds;


    // TODO 추후 공급되는 데이터 자체의 List 수를 제한할 예정
    @Builder
    public static ChampionAnalysisRes toRes(ChampionAnalysis analysis, List<BaseCounter> counters,
        List<BaseCounter> easies) {
        return ChampionAnalysisRes.builder()
            .counterChampions(counters)
            .easyChampions(easies)
            .spellBuilds(analysis.getSummonerSpell())
            .perkBuilds(analysis.getPerks().subList(0, 3))
            .statPerkBuilds(analysis.getStatPerks().subList(0, 2))
            .initialItemBuilds(analysis.getItemStart().subList(0, 2))
            .shoesBuilds(analysis.getItemBoots().subList(0, 2))
            .itemBuilds(analysis.getItemRarity().subList(0, 4))
            .skillBuilds(analysis.getSkillTree().subList(0, 1))
            .build();
    }

}
