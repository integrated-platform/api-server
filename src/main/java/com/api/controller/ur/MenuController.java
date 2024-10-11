package com.api.controller.ur;

import com.api.dto.LoginRequest;
import com.api.dto.LoginResponse;
import com.api.dto.MenuDTO;
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
    public ResponseEntity<ApiResponse<List<MenuDTO>>> getAllMenus(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Authorization 헤더에서 Bearer 토큰 추출
        String token = extractToken(authHeader);

        // 토큰 검증
        if (!isTokenValid(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "유효하지 않거나 누락된 토큰입니다.", null)); // 401 Unauthorized
        }

        // 토큰에서 역할 추출
        String role = jwtUtility.extractRole(token);

        // 역할에 따른 메뉴 요청
        List<MenuDTO> menus = menuService.getMenusByRole(role);

        return ResponseEntity.ok(new ApiResponse<>(true, "메뉴를 성공적으로 조회하였습니다.", menus)); // 200 OK
    }

    // 토큰 추출 메소드
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer " 다음 부분을 추출
        }
        return null;
    }

    // 토큰 유효성 검사 메소드
    private boolean isTokenValid(String token) {
        return token != null && jwtUtility.validateToken(token);
    }


    // 메뉴 접근 권한 확인
}
