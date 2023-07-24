package onlysolorank.apiserver.api.dto;

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
 */

public enum Tier{
    challenger("챌린저"),
    grandmaster("그랜드마스터"),
    master("마스터");


    private String value;

    Tier(String value){
        this.value= value;
    }
}
