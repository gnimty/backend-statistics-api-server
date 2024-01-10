package onlysolorank.apiserver.api.response;

import static onlysolorank.apiserver.api.handler.GlobalExceptionHandler.CustomFieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * packageName    : onlysolorank.apiserver.api.response
 * fileName       : CommonResponse
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/31        solmin       parameter 없는 success 메소드 오버라이드
 * 2023/08/14        solmin       ResponseEntity 리턴은 에러 핸들러에서 처리하는 것으로 변경
 * 2023/08/28        solmin       ApiStatus에 validation error 포함
 */
@Getter
@AllArgsConstructor
@Builder
@Slf4j
public class CommonResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL) // data가 null일 시 json에 미포함
    private ApiStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static CommonResponse success(String message, HttpStatus status) {
        return CommonResponse.builder()
            .status(new ApiStatus(message, status.value()))
            .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder().data(data).build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
            .status(new ApiStatus(errorCode.getMessage(), errorCode.getStatus().value())).build();
    }

    public static CommonResponse fail(ErrorCode errorCode, String message) {
        return CommonResponse.builder()
            .status(new ApiStatus(message, errorCode.getStatus().value())).build();
    }

    public static <T extends CustomFieldError> CommonResponse<T> fail(ErrorCode errorCode, T data) {
        return CommonResponse.<T>builder()
            .status(
                new ApiStatus(data.getMessage(), errorCode.getStatus().value(), data.getField()))
            .build();
    }

    @Data
    @Schema(hidden = true)
    public static class ApiStatus {
        private String message;
        private int code;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String field;

        public ApiStatus(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public ApiStatus(String message, int code, String field) {
            this.message = message;
            this.code = code;
            this.field = field;
        }
    }

}
