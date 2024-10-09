package com.api.controller.ur;

import com.api.dto.LoginRequest;
import com.api.dto.LoginResponse;
import com.api.dto.UserDTO;
import com.api.entity.Menu;
import com.api.entity.User;
import com.api.response.ApiResponse;
import com.api.service.MenuService;
import com.api.service.RoleService;
import com.api.service.UserService;
import com.api.utilty.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JWTUtility jwtUtility;

    @GetMapping
    public List<Menu> getAllMenus(@RequestParam(required = false) String token) {
        if (token == null || !jwtUtility.validateToken(token)) {
            throw new RuntimeException("Invalid or missing token"); // 토큰이 유효하지 않으면 예외 발생
        }
        String role = jwtUtility.extractRole(token); // 토큰에서 역할 추출
        return menuService.getMenusByRole(role); // 역할에 따른 메뉴 요청
    }


    // 메뉴 접근 권한 확인
}
