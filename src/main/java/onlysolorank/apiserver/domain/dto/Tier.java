package onlysolorank.apiserver.domain.dto;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : Tier
 * author         : solmin
 * date           : 2023/07/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/24        solmin       최초 생성
 * 2023/07/31        solmin       확장성을 위한 하위 티어 정보도 추가
 * 2023/08/28        solmin       티어별 기준 mmr 추가
 */

public enum Tier {
    challenger("챌린저", 2800),
    grandmaster("그랜드마스터", 2800),
    master("마스터", 2800),
    diamond("다이아몬드", 2400),
    emerald("에메랄드", 2000),
    platinum("플레티넘", 1600),
    gold("골드", 1200),
    silver("실버", 800),
    bronze("브론즈", 400),
    iron("아이언", 0);

    private final String value;
    private final Integer basisMMR;

    Tier(String value, Integer mmr) {
        this.value = value;
        this.basisMMR = mmr;
    }

    public Integer getBasisMMR() {
        return basisMMR;
    }
}
