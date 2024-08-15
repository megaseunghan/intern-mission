package com.mission.intern.infrastructure.member;

import com.mission.intern.member.domain.entity.Member;
import com.mission.intern.member.domain.repository.MemberRepository;
import com.mission.intern.infrastructure.member.hibernate.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository jpaRepository;

    @Override
    public Member save(Member member) {
        return jpaRepository.save(member);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return jpaRepository.findByUsername(username);
    }
}
