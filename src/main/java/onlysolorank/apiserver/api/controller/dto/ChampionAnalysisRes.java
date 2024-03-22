package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.ChampionTierDto;
import onlysolorank.apiserver.api.service.dto.LaneSelectDto;
import onlysolorank.apiserver.api.service.dto.SummonerPlayWithSummonerDto;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionPatch;
import onlysolorank.apiserver.domain.statistics.analysis.component.CounterStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBootsComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemBuildComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.ItemStartComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.PerkComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SkillComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.SpellComponentStat;
import onlysolorank.apiserver.domain.statistics.analysis.component.StatPerkComponentStat;

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
    private Long championId;

    private Lane lane;
    private ChampionTierDto championTier;
    private List<CounterStat> counterChampions;
    private List<CounterStat> easyChampions;

    private List<SpellComponentStat> spellBuilds;

    private List<PerkComponentStat> perkBuilds;
//    private List<StatPerkComponentStat> statPerkBuilds;

    private List<ItemStartComponentStat> initialItemBuilds;
    private List<ItemBootsComponentStat> shoesBuilds;
    private List<ItemBuildComponentStat> itemBuilds;
    private List<SkillComponentStat> skillBuilds;

    private List<ChampionPatch> patches;

    private List<SummonerPlayWithSummonerDto> specialists;

    private List<LaneSelectDto> laneSelectRates;

    // TODO 추후 공급되는 데이터 자체의 List 수를 제한할 예정
    public static ChampionAnalysisRes toRes(
        ChampionAnalysis analysis,
        ChampionTierDto championTier,
        List<ChampionPatch> patches,
        List<SummonerPlayWithSummonerDto> specialists,
        List<LaneSelectDto> laneSelectRates) {

        return ChampionAnalysisRes.builder()
            .championId(analysis.getChampionId())
            .counterChampions(analysis.getCounters())
            .easyChampions(analysis.getEasy())
            .spellBuilds(analysis.getSummonerSpell())
            .perkBuilds(analysis.getPerks().subList(0, Math.min(analysis.getPerks().size(), 3)))
            .initialItemBuilds(analysis.getItemStart().subList(0, Math.min(analysis.getItemStart().size(), 2)))
            .patches(patches)
            .shoesBuilds(analysis.getItemBoots().subList(0, Math.min(analysis.getItemBoots().size(), 2)))
            .itemBuilds(analysis.getItemBuild().subList(0, Math.min(analysis.getItemBuild().size(), 4)))
            .skillBuilds(analysis.getSkillTree().subList(0, Math.min(analysis.getSkillTree().size(), 1)))
            .lane(analysis.getPosition())
            .championTier(championTier)
            .specialists(specialists)
            .laneSelectRates(laneSelectRates)
            .build();

    }

}
