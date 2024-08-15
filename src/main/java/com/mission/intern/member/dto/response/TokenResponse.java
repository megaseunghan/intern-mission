package com.mission.intern.member.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken
) {

    public static TokenResponse from(String accessToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
