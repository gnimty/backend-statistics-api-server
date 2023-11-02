package onlysolorank.apiserver.api.handler;

import java.util.Arrays;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : GlobalExceptionHandler
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/28        solmin       BindException, CustomException, ConstraintViolationException 공통 에러 처리
 * 2023/08/14        solmin       IllegalArgumentException 공통 예외 처리
 * 2023/08/28        solmin       Validation Error 처리방식 변경: 단일 필드에 대해서만 처리
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 공통 Exception
    @ExceptionHandler({Exception.class})
    public ResponseEntity<CommonResponse> CommonExceptionHandler(Exception ex,
        BindingResult bindingResult) {
        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<CommonResponse> ConversionFailedExceptionHandler(
        ConversionFailedException ex) {
        log.info(ex.getMessage());
        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.INVALID_INPUT_VALUE,
            ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> CustomExceptionHandler(CustomException ex) {

        return new ResponseEntity<>(CommonResponse.fail(ex.getErrorCode(), ex.getMessage()),
            ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse> IllegalArgumentExceptionHandler(
        IllegalArgumentException ex) {
        log.info(ex.getMessage());
        ErrorCode ec = ErrorCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(CommonResponse.fail(ec), ec.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse> BindExceptionHandler(BindException ex,
        BindingResult result) {
        // BindingResult에서 하나만 가져오기
        FieldError err = result.getFieldErrors().stream().findFirst().get();

//        List<ValidateError> validateErrors = result.getFieldErrors().stream()
//            .map(e -> ValidateError.builder()
//                .field(e.getField())
//                .message(e.getDefaultMessage())
//                .build()
//            ).collect(Collectors.toList());

        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.CONSTRAINT_VIOLATION,
            CustomFieldError.builder()
                .message(err.getDefaultMessage())
                .field(err.getField())
                .build()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse> ConstraintViolationExceptionHandler(
        ConstraintViolationException ex) {
        ConstraintViolation<?> violation = ex.getConstraintViolations().stream().findFirst().get();

        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.CONSTRAINT_VIOLATION,
            CustomFieldError.builder()
                .message(violation.getMessage())
                .propertypath(violation.getPropertyPath())
                .build()), HttpStatus.BAD_REQUEST);
    }

    @Data
    public static class CustomFieldError {

        private String field;
        private String message;

        @Builder
        public CustomFieldError(Path propertypath, String field, String message) {
            if (field == null) {
                this.field = Arrays.stream(propertypath.toString().split("\\."))
                    .reduce((first, second) -> second).orElse("none");
            } else {
                this.field = field;
            }
            this.message = message;
        }
    }
}
