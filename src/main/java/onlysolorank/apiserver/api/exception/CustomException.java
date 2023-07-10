package onlysolorank.apiserver.api.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : onlysolorank.apiserver.api.exception
 * fileName       : CustomException
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 */
@Getter
@Builder
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;
    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = message;
    }
}
