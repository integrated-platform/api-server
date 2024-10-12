package com.api.controller.ur;

import com.api.dto.LoginRequest;
import com.api.dto.LoginResponse;
import com.api.dto.UserDTO;
import com.api.entity.Role;
import com.api.entity.User;
import com.api.request.TokenRequest;
import com.api.response.ApiResponse;
import com.api.service.RoleService; // RoleService 추가
import com.api.service.UserService;
import com.api.utilty.JWTUtility;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private JWTUtility jwtUtility;

    // 사용자 가입 메서드
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        // 이메일 중복 체크
        if (userService.isEmailAlreadyInUse(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponse<>(false, "이미 존재하는 이메일입니다.", null));
        }

        // 사용자 엔티티 변환
        User user = userService.convertToEntity(userDTO);

        // 기본 권한 로드 및 설정
        Role userRole = roleService.findUserRole(); // 사용자 권한 객체 조회
        if (userRole == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponse<>(false, "기본 사용자 권한을 찾을 수 없습니다.", null));
        }

        // 사용자에게 권한 부여
        userService.assignRoleToUser(user, userRole);

        // 사용자 저장
        userService.save(user);

        // USER_ROLES 테이블에 관계 추가
        userService.addUserRole(user.getEmail(), userRole.getRoleCode()); // 역할 연결 메서드 호출

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 생성 성공", userService.convertToDto(user)));
    }


    // JWT 토큰 검증 엔드포인트
    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse<Void>> validateToken(@RequestBody TokenRequest request) {
        // JWT 토큰 검증 로직
        boolean isValid = jwtUtility.validateToken(request.getToken());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "잘못된 토큰입니다.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "토큰 검증 성공!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            // 사용자 검증 로직
            User user = userService.findByEmail(loginRequest.getEmail());

            // 비밀번호 검증 실패 시 401 Unauthorized 반환
            if (!userService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new ApiResponse<>(false, "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.", null));
            }

            // JWT 생성
            String accessToken = jwtUtility.generateJWT(user); // Access Token 생성
            String refreshToken = jwtUtility.generateRefreshToken(user); // Refresh Token 생성

            // HTTP-Only 쿠키에 Refresh Token 저장
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true); // 클라이언트 측에서 접근 불가
            refreshTokenCookie.setSecure(true);   // HTTPS에서만 전송
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효기간: 7일
            response.addCookie(refreshTokenCookie); // 쿠키를 응답에 추가

            // Access Token은 JSON으로 응답
            LoginResponse loginResponse = new LoginResponse(accessToken, null); // Refresh Token은 쿠키로 처리했으므로 null

            return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", loginResponse));

        } catch (Exception e) {
            // 서버 내부 오류 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "서버 오류가 발생했습니다.", null));
        }
    }


    // 사용자 정보 조회 메서드
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponse<>(false, "사용자를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 조회 성공", userService.convertToDto(user)));
    }

    // Refresh Token을 사용하여 새로운 Access Token 생성

    // Refresh Token을 이용한 Access Token 재발급 메서드
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@RequestBody TokenRequest request) {
        // Refresh Token 검증
        boolean isValid = jwtUtility.validateRefreshToken(request.getToken());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "잘못된 Refresh Token입니다.", null));
        }

        // Refresh Token이 유효한 경우 새로운 Access Token 생성
        String email = jwtUtility.extractEmailFromRefreshToken(request.getToken()); // Refresh Token에서 이메일 추출
        User user = userService.findByEmail(email); // 이메일로 사용자 찾기

        String newAccessToken = jwtUtility.generateJWT(user); // 새로운 Access Token 생성

        LoginResponse loginResponse = new LoginResponse(newAccessToken, request.getToken()); // 새로운 Access Token과 기존 Refresh Token 반환

        return ResponseEntity.ok(new ApiResponse<>(true, "Access Token 재발급 성공", loginResponse));
    }


    // 메뉴 접근 권한 확인
}
