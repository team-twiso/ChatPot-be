package site.chatpot.domain.user.exception;

import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;

public class NicknameAlreadyExistsException extends ApiException {

    public NicknameAlreadyExistsException() {
        super(ErrorCode.COMMON_ALREADY_EXISTS, "이미 사용중인 닉네임입니다.");
    }

}
