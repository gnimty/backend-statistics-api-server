package onlysolorank.apiserver.domain.statistics.tier;

import lombok.Getter;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.tier
 * fileName       : TierStat
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */
@Getter
public enum ChampionTier {
    OP("OP"),
    TIER_1("1"),
    TIER_2("2"),
    TIER_3("3"),
    TIER_4("4"),
    TIER_5("5"),
    UNKNOWN("정보 없음");

    private String value;


    ChampionTier(String value) {
        this.value = value;
    }
}
