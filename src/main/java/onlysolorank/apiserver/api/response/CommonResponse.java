package onlysolorank.apiserver.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static ResponseEntity<CommonResponse> success() {
        return new ResponseEntity<>(
            CommonResponse.builder().build(),
            HttpStatus.OK);
    }


    public static <T> ResponseEntity<CommonResponse<T>> success(T data) {
        return new ResponseEntity<>(
            CommonResponse.<T>builder().data(data).build(),
            HttpStatus.OK);
    }

    public static <T> ResponseEntity<CommonResponse<T>> success(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(
            CommonResponse.<T>builder().data(data).build(),
            httpStatus);
    }

    public static ResponseEntity<CommonResponse> fail(ErrorCode errorCode) {
        return new ResponseEntity<>(
            CommonResponse.builder().status(new ApiStatus(errorCode.getMessage(), errorCode.getStatus().value())).build(),
            errorCode.getStatus());
    }

    public static <T> ResponseEntity<CommonResponse<T>> fail(ErrorCode errorCode, T data) {
        return new ResponseEntity<>(
            CommonResponse.<T>builder().data(data).status(new ApiStatus(errorCode.getMessage(), errorCode.getStatus().value())).build(),
            errorCode.getStatus());
    }

    @Data
    @AllArgsConstructor
    private static class ApiStatus {
        private String message;
        private int code;
    }

}
