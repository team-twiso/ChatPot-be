package site.chatpot.domain.user.controller.response;

public record UserLoginResponse(
        String accessToken,
        String refreshToken
) {
}
