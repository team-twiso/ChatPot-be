package site.chatpot.domain.user.exception;

import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;

public class PasswordNotMatchedException extends ApiException {
    public PasswordNotMatchedException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER, "잘못된 비밀번호입니다. 다시 입력해 주세요.");
    }
}
