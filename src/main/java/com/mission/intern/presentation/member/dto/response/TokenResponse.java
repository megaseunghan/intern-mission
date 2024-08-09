package com.mission.intern.presentation.member.dto.response;

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
