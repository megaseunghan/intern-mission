package com.mission.intern.infrastructure.member;

import com.mission.intern.domain.member.entity.Member;
import com.mission.intern.domain.member.repository.MemberRepository;
import com.mission.intern.infrastructure.member.hibernate.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository jpaRepository;

    @Override
    public Member save(Member member) {
        return jpaRepository.save(member);
    }
}
