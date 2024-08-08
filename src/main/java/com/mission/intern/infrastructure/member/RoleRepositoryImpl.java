package com.mission.intern.infrastructure.member;

import com.mission.intern.domain.member.entity.Role;
import com.mission.intern.domain.member.repository.RoleRepository;
import com.mission.intern.domain.member.vo.RoleType;
import com.mission.intern.infrastructure.member.hibernate.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository jpaRepository;

    @Override
    public Optional<Role> findByRoleType(RoleType roleType) {
        return jpaRepository.findByRoleType(roleType);
    }
}
