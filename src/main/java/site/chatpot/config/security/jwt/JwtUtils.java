package site.chatpot.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import site.chatpot.config.security.auth.UserDetailsServiceImpl;
import site.chatpot.config.security.exception.TokenExpiredException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TOKEN_TYPE = "tokenType";
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.access-token.expired-at}")
    private String accessTokenExpiredAt;

    @Value("${spring.jwt.refresh-token.expired-at}")
    private String refreshTokenExpiredAt;

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return header.substring(BEARER_PREFIX.length());
    }

    public boolean validateToken(String token) {
        Date exp = parseClaims(token).getExpiration();

        if (exp.before(new Date())) {
            throw new TokenExpiredException();
        }
        return true;
    }

    public String generateToken(String email, String tokenType) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(TOKEN_TYPE, tokenType);

        Date now = new Date();
        Date expiredDate = setExpired(tokenType, now);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Date setExpired(String tokenType, Date now) {
        if (tokenType.equals("accessToken")) {
            return new Date(now.getTime() + Long.parseLong(accessTokenExpiredAt) * 1000);
        }

        if (tokenType.equals("refreshToken")) {
            return new Date(now.getTime() + Long.parseLong(refreshTokenExpiredAt) * 1000);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(parseEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String parseEmail(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
