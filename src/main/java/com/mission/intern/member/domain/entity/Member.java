package com.mission.intern.member.domain.entity;

import com.mission.intern.member.domain.vo.RoleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity(name = "members")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberRole> roles = new HashSet<>();

    private String username;
    private String nickname;
    private String password;

    @Builder
    public Member(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public void setRole(MemberRole memberRole) {
        roles.add(memberRole);
        memberRole.setMember(this);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleType> getAuthorities() {
        return roles.stream()
                .map(MemberRole::getRole)
                .map(Role::getRoleType)
                .collect(Collectors.toSet());
    }
}
