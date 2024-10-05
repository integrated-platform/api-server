package com.api.controller.ur;

import com.api.dto.LoginRequest;
import com.api.dto.LoginResponse;
import com.api.dto.UserDTO;
import com.api.entity.User;
import com.api.response.ApiResponse;
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

    // 사용자 가입 메서드
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.convertToEntity(userDTO);
        userService.save(user);

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 생성 성공", userService.convertToDto(user)));
    }

    // 검증 메서드
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Void>> validateUser(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "사용자 검증 실패: 사용자 이름 또는 비밀번호가 잘못되었습니다.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 검증 성공!", null));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        // 사용자 검증 로직
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null || !userService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "로그인 실패: 사용자 이름 또는 비밀번호가 잘못되었습니다.", null));
        }

        // JWT 생성
        String token = userService.generateJWT(user); // JWT 생성 서비스 호출

        // 로그인 성공 시 LoginResponse 생성
        LoginResponse loginResponse = new LoginResponse(token);

        return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", loginResponse));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        // 사용자 조회 로직 (repository 사용)
        // User user = userRepository.findById(id).orElse(null);
        // return ResponseEntity.ok(userService.convertToDto(user));

        return ResponseEntity.ok(new UserDTO(id, "Sample Name", "sample@example.com", "password")); // 예시 응답
    }


    // 다른 메서드 추가 가능 (update, delete 등)
}
