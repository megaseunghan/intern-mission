package com.mission.intern.global.security;

import com.mission.intern.member.domain.entity.Member;
import com.mission.intern.member.domain.entity.MemberRole;
import com.mission.intern.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        Set<MemberRole> roles = member.getRoles();

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(memberRole -> new SimpleGrantedAuthority(memberRole.getRole().getRoleType().name()))
                .collect(Collectors.toSet());

        return new User(member.getUsername(), "", authorities);
    }
}
