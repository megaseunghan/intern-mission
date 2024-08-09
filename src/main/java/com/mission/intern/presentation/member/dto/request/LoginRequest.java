package com.mission.intern.presentation.member.dto.request;

public record LoginRequest(
        String username,
        String password
) {
}
