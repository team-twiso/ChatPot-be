package site.chatpot.domain.user.exception;

import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;

public class EmailAlreadyExistsException extends ApiException {

    public EmailAlreadyExistsException() {
        super(ErrorCode.COMMON_ALREADY_EXISTS, "이미 가입된 이메일입니다.");
    }
}
