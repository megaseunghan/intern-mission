package com.mission.intern.member.infrastructure.hibernate;


import com.mission.intern.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
