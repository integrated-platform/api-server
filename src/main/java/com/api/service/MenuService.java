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

    // 모든 메뉴를 조회하는 메서드
    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    // 특정 메뉴를 코드로 조회하는 메서드
    public Optional<Menu> findMenuByCode(String menuCode) {
        return menuRepository.findByMenuCode(menuCode);
    }

    // 메뉴 생성 메서드
    @Transactional
    public Menu createMenu(Menu menu) {
        // 새 메뉴를 저장
        return menuRepository.save(menu);
    }

    // 메뉴 수정 메서드
    @Transactional
    public Menu updateMenu(String menuCode, Menu menuDetails) {
        Menu existingMenu = menuRepository.findByMenuCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        // 메뉴의 속성 업데이트
        existingMenu.setCommonCode(menuDetails.getCommonCode());
        existingMenu.setParent(menuDetails.getParent());
        existingMenu.setMenuRole(menuDetails.getMenuRole());
        existingMenu.setMenuType(menuDetails.getMenuType());

        return menuRepository.save(existingMenu);
    }

    // 메뉴 삭제 메서드
    @Transactional
    public void deleteMenu(String menuCode) {
        Menu existingMenu = menuRepository.findByMenuCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        menuRepository.delete(existingMenu);
    }

    // 사용자 권한에 따라 메뉴 접근을 확인하는 메서드
    public boolean hasAccessToMenu(String userEmail, String menuCode) {
        // 사용자 권한 조회 로직을 여기에 추가
        // 예를 들어 RoleService를 사용하여 역할을 가져올 수 있습니다.

        // 특정 메뉴 조회
        Menu menu = menuRepository.findByMenuCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        // 메뉴의 역할과 사용자의 역할을 비교
        // 이 부분은 실제 구현에 맞게 조정해야 합니다.
        String menuRole = menu.getMenuRole();

        // 사용자 권한 조회 로직이 여기에 들어가야 함
        // 예시: String userRole = roleService.getUserRole(userEmail);
        String userRole = "ROLE_USER"; // 예시로 하드코딩된 값

        return menuRole.equals(userRole); // 예제이므로, 실제 구현에서는 사용자 권한과 메뉴 역할을 비교해야 합니다.
    }
}
