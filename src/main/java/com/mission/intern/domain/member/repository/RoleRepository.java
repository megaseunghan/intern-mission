package com.mission.intern.domain.member.repository;

import com.mission.intern.domain.member.entity.Role;
import com.mission.intern.domain.member.vo.RoleType;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRoleType(RoleType roleType);

}
