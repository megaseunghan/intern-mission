package com.mission.intern.member.dto.request;

import com.mission.intern.member.domain.entity.Member;
import com.mission.intern.member.domain.vo.RoleType;

public record RegisterRequest(
        String username,
        String nickname,
        String password,
        RoleType role
) {
    public Member of() {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .build();
    }
}
