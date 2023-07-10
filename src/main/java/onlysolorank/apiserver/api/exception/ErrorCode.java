package onlysolorank.apiserver.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 */

@Getter
public enum ErrorCode {
    // Error Code 정의
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
