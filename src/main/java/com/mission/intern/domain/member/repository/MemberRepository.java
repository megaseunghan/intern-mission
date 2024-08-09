package com.mission.intern.domain.member.repository;

import com.mission.intern.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByUsername(String username);
}
