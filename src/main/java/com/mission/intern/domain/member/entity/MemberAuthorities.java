package com.mission.intern.domain.member.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity(name = "members_authorities")
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberAuthoritiesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
