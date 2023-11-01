package onlysolorank.apiserver.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * packageName    : onlysolorank.apiserver.api.exception
 * fileName       : ErrorCode
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/28        solmin       CONSTRAINT_VIOLATION 추가
 * 2023/07/31        solmin       RESULT_NOT_FOUND 추가
 * 2023/08/29        solmin       전적 갱신 시 발생할 수 있는 에러 상황 추가
 */

@Getter
public enum ErrorCode {
    // Error Code 정의
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Input Value Error"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "입력값 중 검증에 실패한 값이 있습니다."),
    RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 결과가 존재하지 않습니다."),
    SUMMONER_REFRESH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "소환사 정보 갱신에 실패했습니다. 다시 시도해주세요."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "소환사에 대한 요청이 너무 많습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
