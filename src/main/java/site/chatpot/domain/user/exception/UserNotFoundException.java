package site.chatpot.domain.user.exception;

import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND, "해당 사용자를 찾을 수 없습니다.");
    }
}
