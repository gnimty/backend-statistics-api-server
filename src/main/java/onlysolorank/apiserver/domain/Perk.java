package onlysolorank.apiserver.domain;

import lombok.Getter;
import lombok.ToString;

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
 */
@Getter
@ToString
public class Perk {
    public Perk(StatPerk statPerks, List<PerkDetail> styles) {
        this.statPerks = statPerks;
        this.styles = styles;
    }

    private StatPerk statPerks;
    private List<PerkDetail> styles;

    @Getter
    @ToString
    private static class StatPerk{
        private Integer defense;
        private Integer offense;
        private Integer flex;

        public StatPerk(Integer defense, Integer offense, Integer flex) {
            this.defense = defense;
            this.offense = offense;
            this.flex = flex;
        }
    }

    @Getter
    @ToString
    private class PerkDetail {
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
    private class Selection {
        private Integer perk;
        private Integer var1;
        private Integer var2;
        private Integer var3;

        public Selection(Integer perk, Integer var1, Integer var2, Integer var3) {
            this.perk = perk;
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }
    }
}
