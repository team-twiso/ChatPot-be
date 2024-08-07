package site.chatpot.domain.common.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.chatpot.domain.common.exception.BaseException;
import site.chatpot.domain.common.response.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CommonResponse<Void>> handleBaseException(BaseException e) {
        HttpStatus status = HttpStatus.valueOf(e.getErrorCode().getCode());
        CommonResponse<Void> response = CommonResponse.fail(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, status);
    }
}
