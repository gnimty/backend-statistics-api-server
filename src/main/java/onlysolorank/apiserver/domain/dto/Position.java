package onlysolorank.apiserver.domain.dto;

import lombok.extern.slf4j.Slf4j;

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

@Slf4j
public enum Position {
    TOP("탑"),
    JUNGLE("정글"),
    MIDDLE("미드"),
    BOTTOM("원딜"),
    UTILITY("서폿"),
    UNKNOWN("알 수 없음");

    private final String value;

    Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Position defaultValue() {
        return UNKNOWN;
    }

}
