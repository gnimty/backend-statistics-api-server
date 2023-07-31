package onlysolorank.apiserver.domain;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Position
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 */
public enum Position {
    TOP("탑"),
    JUNGLE("정글"),
    MIDDLE("미드"),
    BOTTOM("원딜"),
    UTILITY("서폿");

    private String value;

    Position(String value){
        this.value= value;
    }
}
