package com.mission.intern.member.application;

import com.mission.intern.domain.member.entity.Member;
import com.mission.intern.domain.member.entity.MemberRole;
import com.mission.intern.domain.member.entity.Role;
import com.mission.intern.domain.member.repository.MemberRepository;
import com.mission.intern.domain.member.repository.RoleRepository;
import com.mission.intern.domain.member.vo.RoleType;
import com.mission.intern.global.jwt.JwtUtil;
import com.mission.intern.member.dto.request.LoginRequest;
import com.mission.intern.member.dto.request.RegisterRequest;
import com.mission.intern.member.dto.response.RegisteredMemberInfo;
import com.mission.intern.member.dto.response.TokenResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;

    @Transactional
    public RegisteredMemberInfo register(RegisterRequest request) {
        Member member = memberRepository.save(request.of());
        Role role = getRole(request.role());
        MemberRole memberRole = new MemberRole(role);

        member.setPassword(passwordEncoder.encode(request.password()));
        member.setRole(memberRole);

        return RegisteredMemberInfo.from(member);
    }

    public TokenResponse login(LoginRequest request) {
        Member member = getMemberByUsername(request.username());

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Set<SimpleGrantedAuthority> authorities = member.getAuthorities().stream()
                .map(roleType -> new SimpleGrantedAuthority(roleType.name()))
                .collect(Collectors.toSet());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getPassword(), authorities);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenResponse response = jwtUtil.generateJwt(authentication);

        return response;
    }

    private Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

    private Role getRole(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new IllegalArgumentException("해당 권한은 찾을 수 없습니다."));
    }

}
