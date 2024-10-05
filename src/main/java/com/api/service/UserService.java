package com.api.service;

import com.api.dto.UserDTO;
import com.api.entity.User;
import com.api.repository.UserRepository;
import com.api.utilty.PasswordUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final String SECRET_KEY = "your_secret_key"; // 비밀 키 (환경 변수로 관리하는 것이 좋음)
    private final long EXPIRATION_TIME = 86400000; // 1일 (24시간)

    private final UserRepository userRepository;

    @Autowired // 생성자 주입 방식
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // UserDTO를 User Entity로 변환
    public User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail());
    }

    // User Entity를 UserDTO로 변환
    public UserDTO convertToDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    // 사용자 저장 로직
    public User save(User user) {
        // 비밀번호 암호화
        String hashedPassword = PasswordUtils.encryptPassword(user.getPassword());
        user.setPassword(hashedPassword); // 암호화된 비밀번호 설정
        return userRepository.save(user); // 저장
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encryptedPassword) {
        return PasswordUtils.verifyPassword(rawPassword, encryptedPassword);
    }

    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && verifyPassword(password, user.getPassword());
    }

    // JWT 생성 메서드
    public String generateJWT(User user) {
        // JWT 생성
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("username", user.getUsername());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateToken(String email, String password) {
        // 사용자 인증 로직
        User user = userRepository.findByEmail(email);
        if (user == null || !verifyPassword(password, user.getPassword())) {
            return null; // 인증 실패
        }

        // JWT 생성 로직 추가 (예: Jwts.builder()를 사용하여 토큰 생성)
        return "generated-jwt-token"; // 실제 JWT로 대체
    }


}
