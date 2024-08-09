package com.mission.intern.global.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String issuer,
        String secret,
        Long accessTokenExpirationHour,
        Long refreshTokenExpirationHour
) {
}
