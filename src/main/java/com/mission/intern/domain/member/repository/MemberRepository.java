package com.mission.intern.domain.member.repository;

import com.mission.intern.domain.member.entity.Member;

public interface MemberRepository {
    Member save(Member member);
}
