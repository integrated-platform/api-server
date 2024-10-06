package com.api.repository;

import com.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleCode(String roleCode);

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.email = :email")
    Set<Role> findRolesByEmail(@Param("email") String email);
}

