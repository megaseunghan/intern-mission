package com.mission.intern.infrastructure.member.hibernate;


import com.mission.intern.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
