package com.api.service;

import com.api.entity.Role;
import com.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository; // RoleRepository 주입

    // 기본 사용자 권한 코드를 찾는 메서드
    public String findUserRoleCode() {
        // 기본 사용자 권한 코드를 통해 권한을 조회
        Role userRole = roleRepository.findByRoleCode("R000001").orElse(null); // 예시로 "R000001" 역할 코드 사용
        return userRole != null ? userRole.getRoleCode() : null; // 역할 코드 반환
    }

    // 기본 사용자 역할을 찾는 메서드
    public Role findUserRole() {
        // 역할 코드가 "USER"인 Role을 찾아서 반환
        return roleRepository.findByRoleCode("USER")
                .orElse(null); // 없으면 null 반환
    }
}
