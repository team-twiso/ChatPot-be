package site.chatpot.domain.common.exception.exceptionhandler;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;
import site.chatpot.domain.common.exception.ValidationError;
import site.chatpot.domain.common.response.Api;
import site.chatpot.domain.user.entity.enums.Gender;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Void>> handleApiException(ApiException e) {
        log.error(e.getMessage(), e);
        HttpStatus status = HttpStatus.valueOf(e.getErrorCode().getCode());
        Api<Void> response = Api.fail(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<List<ValidationError>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        List<ValidationError> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    String field = error.getField();
                    String rejectedValue = String.valueOf(error.getRejectedValue());
                    String message = error.getDefaultMessage();
                    if ("gender".equals(field) && rejectedValue != null) {
                        message = Gender.errorMessage();
                    }
                    return new ValidationError(field, rejectedValue, message);
                })
                .toList();
        Api<List<ValidationError>> response = Api.fail(ErrorCode.COMMON_INVALID_PARAMETER, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
