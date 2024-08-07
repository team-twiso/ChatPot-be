package site.chatpot.domain.common.exception.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.response.Api;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Void>> handleApiException(ApiException e) {
        log.error("", e);
        HttpStatus status = HttpStatus.valueOf(e.getErrorCode().getCode());
        Api<Void> response = Api.fail(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, status);
    }
}
