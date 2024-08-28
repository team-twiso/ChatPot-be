package site.chatpot.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JwtProperties(
        AccessToken accessToken,
        RefreshToken refreshToken,
        String secretKey
) {
    public record AccessToken(
            String expiredAt
    ) {
    }

    public record RefreshToken(
            String expiredAt
    ) {
    }
}
