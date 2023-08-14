package onlysolorank.apiserver.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.ErrorCode;

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

    public static CommonResponse success() {
        return CommonResponse.builder().build();
    }


    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder().data(data).build();
    }

//    public static <T> CommonResponse<T> success(T data, HttpStatus httpStatus) {
//        return CommonResponse.<T>builder().data(data).status(httpStatus).build());
//    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
            .status(new ApiStatus(errorCode.getMessage(), errorCode.getStatus().value())).build();
    }

    public static <T> CommonResponse<T> fail(ErrorCode errorCode, T data) {
        return CommonResponse.<T>builder().data(data)
            .status(new ApiStatus(errorCode.getMessage(), errorCode.getStatus().value())).build();
    }

    @Data
    @AllArgsConstructor
    private static class ApiStatus {
        private String message;
        private int code;
    }

}
