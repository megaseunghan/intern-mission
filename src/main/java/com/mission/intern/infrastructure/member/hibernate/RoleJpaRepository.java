package com.mission.intern.infrastructure.member.hibernate;

import com.mission.intern.member.domain.entity.Role;
import com.mission.intern.member.domain.vo.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);
}
