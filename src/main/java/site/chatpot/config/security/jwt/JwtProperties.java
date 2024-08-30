package site.chatpot.config.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JwtProperties(

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
