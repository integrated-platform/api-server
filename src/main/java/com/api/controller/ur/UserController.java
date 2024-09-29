package com.api.controller.ur;

import com.api.dto.UserDTO;
import com.api.entity.User;
import com.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        // DTO를 엔티티로 변환 후 저장 로직
        User user = userService.convertToEntity(userDTO);
        // userRepository.save(user);  // 사용자 저장 로직 (repository 사용)

        // 저장된 엔티티를 DTO로 변환하여 반환
        return ResponseEntity.ok(userService.convertToDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        // 사용자 조회 로직 (repository 사용)
        // User user = userRepository.findById(id).orElse(null);
        // return ResponseEntity.ok(userService.convertToDto(user));
        return ResponseEntity.ok(new UserDTO(id, "Sample Name", "sample@example.com")); // 예시 응답
    }

    // 다른 메서드 추가 가능 (update, delete 등)
}
