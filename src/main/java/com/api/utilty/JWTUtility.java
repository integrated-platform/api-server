package com.api.utilty;


import com.api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtility {

    private final String SECRET_KEY = "your_secret_key"; // 비밀 키
    private final long EXPIRATION_TIME = 86400000; // 1일 (24시간)

    // JWT 생성 메소드
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

    // JWT 검증 메소드
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false; // 검증 실패
        }
    }

    // JWT에서 사용자 이름 추출 메소드
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // JWT에서 역할 추출 메소드
    public String extractRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }
}