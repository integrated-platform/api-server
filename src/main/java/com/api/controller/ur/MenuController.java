package com.api.controller.ur;

import com.api.dto.LoginRequest;
import com.api.dto.LoginResponse;
import com.api.dto.UserDTO;
import com.api.entity.User;
import com.api.response.ApiResponse;
import com.api.service.MenuService;
import com.api.service.RoleService;
import com.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/{menuId}/access/{email}")
    public ResponseEntity<String> checkMenuAccess(@PathVariable Long menuId, @PathVariable String email) {
        boolean hasAccess = menuService.hasAccessToMenu(email, menuId);

        if (hasAccess) {
            return ResponseEntity.ok("접근 가능");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 불가");
        }
    }


    // 메뉴 접근 권한 확인
}
