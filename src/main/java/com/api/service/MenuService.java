package com.api.service;

import com.api.entity.Menu;
import com.api.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenusByRole(String role) {
        return menuRepository.findByMenuRoleIn(List.of(role)); // 단일 역할 요청
    }

    public List<Menu> getMenusByRoles(List<String> roles) {
        return menuRepository.findByMenuRoleIn(roles); // 여러 역할 요청
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll(); // 모든 메뉴 요청
    }
}
