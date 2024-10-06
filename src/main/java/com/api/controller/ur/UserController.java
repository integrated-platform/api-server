package com.api.controller.ur;

import com.api.dto.LoginRequest;
import com.api.dto.LoginResponse;
import com.api.dto.UserDTO;
import com.api.entity.User;
import com.api.response.ApiResponse;
import com.api.service.RoleService; // RoleService 추가
import com.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService; // RoleService 주입

    // 사용자 가입 메서드
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        // 이메일 중복 체크
        if (userService.isEmailAlreadyInUse(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, "이미 존재하는 이메일입니다.", null));
        }

        // 사용자 엔티티 변환
        User user = userService.convertToEntity(userDTO);

        // 기본 권한 로드 및 설정
        Long userRoleId = roleService.findUserRoleId(); // 사용자 권한 ID 조회
        if (userRoleId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "기본 사용자 권한을 찾을 수 없습니다.", null));
        }
        userService.assignRoleToUser(user, userRoleId); // 사용자에게 권한 부여

        // 사용자 저장
        userService.save(user);

        // USER_ROLES 테이블에 관계 추가
        userService.addUserRole(user.getId(), userRoleId); // 역할 연결 메서드 호출


        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 생성 성공", userService.convertToDto(user)));
    }

    // 사용자 검증 메서드
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Void>> validateUser(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "사용자 검증 실패: 이메일 또는 비밀번호가 잘못되었습니다.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 검증 성공!", null));
    }

    // 로그인 메서드 (JWT 생성 포함)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        // 사용자 검증 로직
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null || !userService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.", null));
        }

        // JWT 생성
        String token = userService.generateJWT(user); // JWT 생성 서비스 호출

        // 로그인 성공 시 LoginResponse 생성
        LoginResponse loginResponse = new LoginResponse(token);

        return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", loginResponse));
    }

    // 사용자 정보 조회 메서드
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "사용자를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 조회 성공", userService.convertToDto(user)));
    }


    // 메뉴 접근 권한 확인
}
