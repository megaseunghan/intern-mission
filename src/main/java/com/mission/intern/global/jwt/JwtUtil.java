package com.mission.intern.global.jwt;

import com.mission.intern.presentation.member.dto.response.TokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil implements InitializingBean {

    private final JwtProperties jwtProperties;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes());
    }

    public TokenResponse generateJwt(Authentication authentication) {
        String accessToken = generateAccessToken(authentication);
        String refreshToken = generateRefreshToken(authentication);

        // TODO: refreshToken 저장

        return TokenResponse.from(accessToken);
    }

    public Set<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public boolean validateToken(String accessToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(accessToken);
            return true;
        } catch (MalformedJwtException | SecurityException e) {
            log.info("토큰의 형식이 올바르지 않습니다");
        } catch (ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("올바르지 않은 토큰 접근입니다.");
        } catch (NullPointerException e) {
            log.info("토큰이 존재하지 않습니다");
        }
        return false;
    }

    public Claims getClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private String generateAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(authentication.getName())
                .issuer(jwtProperties.issuer())
                .expiration(Date.from(now.plus(Duration.ofHours(jwtProperties.accessTokenExpirationHour()))))
                .signWith(key)
                .claim("auth", authorities)
                .compact();
    }

    private String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        return Jwts.builder()
                .expiration(Date.from(now.plus(Duration.ofHours(jwtProperties.refreshTokenExpirationHour()))))
                .signWith(key)
                .compact();
    }
}
