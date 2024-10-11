package com.api.service;

import com.api.dto.MenuDTO;
import com.api.entity.Menu;
import com.api.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenusByRoles(List<String> roles) {
        return menuRepository.findByMenuRoleIn(roles); // 여러 역할 요청
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll(); // 모든 메뉴 요청
    }

    public List<MenuDTO> getMenusByRole(String role) {
        List<Menu> menus = menuRepository.findByMenuRoleIn(List.of(role));
        return menus.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private MenuDTO convertToDto(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuCode(menu.getMenuCode());
        menuDTO.setName(menu.getCommonCode().getName());
        menuDTO.setRoute(menu.getRoute());
        menuDTO.setType(menu.getMenuType()); // 메뉴 유형 설정
        menuDTO.setIcon(menu.getIcon()); // 메뉴 아이콘 설정
        menuDTO.setChildren(convertChildren(menu.getChildren())); // 하위 메뉴 변환
        return menuDTO;
    }

    // 하위 메뉴 변환 메소드
    private List<MenuDTO> convertChildren(List<Menu> children) {
        return children.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
