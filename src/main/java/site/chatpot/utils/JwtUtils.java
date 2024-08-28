package site.chatpot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
    private static final String TOKEN_TYPE = "tokenType";
    private final JwtProperties properties;

    public String generateToken(String email, String tokenType) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(TOKEN_TYPE, tokenType);

        Date now = new Date();
        Date expiredDate = setExpired(tokenType, now);

        Key key = Keys.hmacShaKeyFor(properties.secretKey().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Date setExpired(String tokenType, Date now) {
        if (tokenType.equals("accessToken")) {
            return new Date(now.getTime() + Long.parseLong(properties.accessToken().expiredAt()));
        }

        if (tokenType.equals("refreshToken")) {
            return new Date(now.getTime() + Long.parseLong(properties.refreshToken().expiredAt()));
        }
        return null;
    }
}
