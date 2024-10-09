package com.api.service;

import com.api.dto.UserDTO;
import com.api.entity.Role;
import com.api.entity.User;
import com.api.entity.UserRole;
import com.api.repository.RoleRepository;
import com.api.repository.UserRepository;
import com.api.repository.UserRoleRepository;
import com.api.utilty.PasswordUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final String SECRET_KEY = "your_secret_key"; // 비밀 키 (환경 변수로 관리하는 것이 좋음)
    private final long EXPIRATION_TIME = 86400000; // 1일 (24시간)

    @Autowired
    private  UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository; // RoleRepository 주입

    @Autowired
    private UserRoleRepository userRoleRepository;


    // 이메일 중복 체크 로직 추가
    public boolean isEmailAlreadyInUse(String email) {
        return userRepository.existsByEmail(email);
    }



    // UserDTO를 User Entity로 변환
    public User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail());
    }

    // User Entity를 UserDTO로 변환
    public UserDTO convertToDto(User user) {
        return new UserDTO(user.getUsername(), user.getPassword(), user.getEmail());
    }

    // 사용자 저장 로직
    public User save(User user) {
        // 비밀번호 암호화
        String hashedPassword = PasswordUtils.encryptPassword(user.getPassword());
        user.setPassword(hashedPassword); // 암호화된 비밀번호 설정
        return userRepository.save(user); // 저장
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + email));
        return user;
    }

    public boolean verifyPassword(String rawPassword, String encryptedPassword) {
        return PasswordUtils.verifyPassword(rawPassword, encryptedPassword);
    }

    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + email));
        return user != null && verifyPassword(password, user.getPassword());
    }

    @Transactional
    public void assignRoleToUser(User user, Role role) {
        if (role == null) {
            throw new RuntimeException("권한이 null입니다."); // null 체크 추가
        }
        user.getRoles().add(role); // 사용자 엔티티의 권한 집합에 권한 추가
    }

    public void addUserRole(String email, String roleCode) {
        // 이메일과 역할 코드가 null인지 체크
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("이메일이 null이거나 비어 있습니다.");
        }
        if (roleCode == null || roleCode.isEmpty()) {
            throw new RuntimeException("역할 코드가 null이거나 비어 있습니다.");
        }

        // 사용자 이메일로 User 객체를 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + email));

        // 역할 코드로 Role 객체를 조회
        Role role = roleRepository.findByRoleCode(roleCode)
                .orElseThrow(() -> new RuntimeException("역할을 찾을 수 없습니다: " + roleCode));

        // USER_ROLES 테이블에 역할 연결
        userRoleRepository.save(new UserRole(user, role)); // UserRole 엔티티 생성 후 저장
    }


}
