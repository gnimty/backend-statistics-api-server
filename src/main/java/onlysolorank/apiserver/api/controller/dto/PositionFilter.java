package onlysolorank.apiserver.api.controller.dto;

import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : PositionFilter
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */
@Slf4j
public enum PositionFilter {
    TOP("탑"),
    JUNGLE("정글"),
    MIDDLE("미드"),
    BOTTOM("원딜"),
    UTILITY("서폿"),
    UNKNOWN("잘못된 포지션 정보"), // 잘못된 포지션 입력 시
    ALL("모든 포지션"); // 상관 없음

    private final String value;

    PositionFilter(String value) {
        this.value = value;
    }

//    public static PositionFilter defaultValue() {
//        return UNKNOWN;
//    }

    public String getValue() {
        return value;
    }

}
