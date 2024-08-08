package com.mission.intern.application.member;

import com.mission.intern.domain.member.entity.MemberRole;
import com.mission.intern.domain.member.entity.Role;
import com.mission.intern.domain.member.repository.MemberRepository;
import com.mission.intern.domain.member.entity.Member;
import com.mission.intern.domain.member.repository.RoleRepository;
import com.mission.intern.domain.member.vo.RoleType;
import com.mission.intern.presentation.member.dto.request.RegisterRequest;
import com.mission.intern.presentation.member.dto.response.RegisteredMemberInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public RegisteredMemberInfo register(RegisterRequest request) {
        Member member = memberRepository.save(request.of());
        Role role = getRole(request.role());
        MemberRole memberRole = new MemberRole(role);

        member.setRole(memberRole);

        return RegisteredMemberInfo.from(member);
    }

    private Role getRole(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new IllegalArgumentException("해당 권한은 찾을 수 없습니다."));
    }

}
