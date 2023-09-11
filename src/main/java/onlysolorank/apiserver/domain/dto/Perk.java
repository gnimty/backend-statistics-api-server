package onlysolorank.apiserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.api.service.dto.SpectatorV4GetCurrentGameInfo;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Perk
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 * 2023/08/30        solmin       Riot API로부터 요청받은 데이터 processing을 위한 생성자 오버라이딩
 * 2023/08/06        solmin       PerkDetail 구조 수정: Selections to Integer (Perk ID)
 * 2023/09/11        solmin       08/06 변경사항 삭제
 */
@Getter
@ToString
public class Perk {
    public Perk(StatPerk statPerks, List<PerkDetail> styles) {
        this.statPerks = statPerks;
        this.styles = styles;
    }

    public static Perk getPerk(SpectatorV4GetCurrentGameInfo.IngamePerks perks){
        List<Long> perkIds = perks.getPerkIds();

        Integer offense = perkIds.get(6).intValue();
        Integer flex = perkIds.get(7).intValue();
        Integer defense = perkIds.get(8).intValue();


        List<Long> primaryPerkIds = perkIds.subList(0, 4);
        List<Long> subPerkIds = perkIds.subList(4, 6);

        PerkDetail primaryStyle = new PerkDetail(
            "primaryStyle",
            perks.getPerkStyle().intValue(),
            primaryPerkIds.stream().map(ps->new Selection(ps.intValue(), null, null, null)).toList());

        PerkDetail subStyle = new PerkDetail(
            "subStyle",
            perks.getPerkStyle().intValue(),
            subPerkIds.stream().map(ps->new Selection(ps.intValue(), null, null, null)).toList());
        StatPerk statPerks = StatPerk.builder().defense(defense).flex(flex).offense(offense).build();
        List<PerkDetail> styles = List.of(primaryStyle, subStyle);

        return new Perk(statPerks, styles);
    }

    private final StatPerk statPerks;
    private List<PerkDetail> styles;

    @Getter
    @ToString
    private static final class StatPerk{
        private Integer defense;
        private Integer offense;
        private Integer flex;

        @Builder
        public StatPerk(Integer defense, Integer offense, Integer flex) {
            this.defense = defense;
            this.offense = offense;
            this.flex = flex;
        }
    }

    @Getter
    @ToString
    private static class PerkDetail {
        private String description;
        private Integer style;
        private List<Selection> selections;

        public PerkDetail(String description, Integer style, List<Selection> selections) {
            this.description = description;
            this.style = style;
            this.selections = selections;
        }
    }

    @Getter
    @ToString
    private static class Selection {
        private Integer perk;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer var1;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer var2;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer var3;

        public Selection(Integer perk, Integer var1, Integer var2, Integer var3) {
            this.perk = perk;
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }
    }
}
