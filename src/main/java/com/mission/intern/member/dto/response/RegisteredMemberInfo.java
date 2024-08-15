package com.mission.intern.member.dto.response;

import com.mission.intern.domain.member.entity.Member;
import com.mission.intern.domain.member.vo.RoleType;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record RegisteredMemberInfo(
        String username,
        String nickname,
        Set<AuthorityResponse> authorities
) {

    public static RegisteredMemberInfo from(Member member) {
        Set<AuthorityResponse> authorities = member.getAuthorities().stream()
                .map(RoleType::name)
                .map(AuthorityResponse::new)
                .collect(Collectors.toSet());

        return RegisteredMemberInfo.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .authorities(authorities)
                .build();
    }
}
