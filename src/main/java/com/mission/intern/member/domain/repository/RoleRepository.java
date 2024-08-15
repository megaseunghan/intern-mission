package com.mission.intern.member.domain.repository;

import com.mission.intern.member.domain.entity.Role;
import com.mission.intern.member.domain.vo.RoleType;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRoleType(RoleType roleType);

}
