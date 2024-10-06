package com.api.service;

import com.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository; // RoleRepository 주입

    // 기본 사용자 권한 ID를 찾는 메서드
    public Long findUserRoleId() {
        // 기본 사용자 권한 코드를 통해 권한 ID를 조회
        return roleRepository.findByRoleCode("ROLE_USER").getId(); // 예시로 "USER" 권한 코드 사용
    }
}
