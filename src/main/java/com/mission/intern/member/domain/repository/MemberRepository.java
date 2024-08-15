package com.mission.intern.member.domain.repository;

import com.mission.intern.member.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByUsername(String username);
}
