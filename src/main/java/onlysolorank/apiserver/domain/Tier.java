package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;


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
 */

public enum Tier{
    challenger("챌린저"),
    grandmaster("그랜드마스터"),
    master("마스터"),
    diamond("다이아몬드"),
    emerald("에메랄드"),
    platinum("플레티넘"),
    gold("골드"),
    silver("실버"),
    bronze("브론즈"),
    iron("아이언");

    private String value;

    Tier(String value){
        this.value= value;
    }
}
