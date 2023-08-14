package onlysolorank.apiserver.api.handler;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.api.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 공통 Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> CommonExceptionHandler(Exception ex, BindingResult bindingResult) {
        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> CustomExceptionHandler(CustomException ex) {

        return new ResponseEntity<>(CommonResponse.fail(ex.getErrorCode()), ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {

        ErrorCode ec = ErrorCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(CommonResponse.fail(ec), ec.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<List<ValidateError>>> BindExceptionHandler(BindException ex, BindingResult result) {

        List<ValidateError> validateErrors = result.getFieldErrors().stream()
            .map(e -> ValidateError.builder()
                .field(e.getField())
                .message(e.getDefaultMessage())
                .build()
            ).collect(Collectors.toList());

        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.CONSTRAINT_VIOLATION, validateErrors),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<List<ValidateError>>> ConstraintViolationExceptionHandler(ConstraintViolationException ex) {
        List<ValidateError> validateErrors= new ArrayList<>();

        ex.getConstraintViolations()
            .stream()
            .forEach(constraintViolation -> {
                validateErrors.add(ValidateError.builder()
                    .propertypath(constraintViolation.getPropertyPath())
                    .message(constraintViolation.getMessage())
                    .build());
            });

        return new ResponseEntity<>(CommonResponse.fail(ErrorCode.CONSTRAINT_VIOLATION, validateErrors),HttpStatus.BAD_REQUEST);
    }

    @Data
    private static class ValidateError{
        private String field;
        private String message;

        @Builder
        public ValidateError(Path propertypath, String field, String message){
            if (field ==null){
                this.field =  Arrays.stream(propertypath.toString().split("\\.")).reduce((first, second) -> second).orElse("none");
            }else{
                this.field = field;
            }
            this.message = message;
        }
    }
}
