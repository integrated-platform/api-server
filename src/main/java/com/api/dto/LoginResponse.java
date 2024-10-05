package com.api.dto;

public class LoginResponse {
    private String token; // JWT 토큰 필드 추가

    public LoginResponse( String token) { // 생성자 매개변수에 token 추가
        this.token = token; // 내부 필드에 token 할당
    }


    public String getToken() { // Getter 메서드 추가
        return token;
    }
}
