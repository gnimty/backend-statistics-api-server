package onlysolorank.apiserver.domain.dto;

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

public enum Lane {
    TOP("탑"),
    JUNGLE("정글"),
    MIDDLE("미드"),
    BOTTOM("원딜"),
    UTILITY("서폿"),
    UNKNOWN("알 수 없음"),
    ALL("상관 없음");

    private final String value;

    Lane(String value) {
        this.value = value;
    }

    public static Lane defaultValue() {
        return UNKNOWN;
    }

    public static Lane[] getActualLane() {
        Lane[] targets = {TOP, JUNGLE, MIDDLE, BOTTOM, UTILITY};
        return targets;
    }

    public String getValue() {
        return value;
    }

}