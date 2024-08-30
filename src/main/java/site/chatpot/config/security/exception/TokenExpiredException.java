package site.chatpot.config.security.exception;

import site.chatpot.domain.common.exception.ApiException;
import site.chatpot.domain.common.exception.ErrorCode;

public class TokenExpiredException extends ApiException {
    public TokenExpiredException() {
        super(ErrorCode.SECURITY_UNAUTHORIZED, "만료된 토큰입니다.");
    }
}
