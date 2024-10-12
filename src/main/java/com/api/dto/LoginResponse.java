package com.api.dto;

public class LoginResponse {
    private String accessToken; // Access Token 필드
    private String refreshToken; // Refresh Token 필드 추가

    // 생성자
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken; // 내부 필드에 accessToken 할당
        this.refreshToken = refreshToken; // 내부 필드에 refreshToken 할당
    }

    // Access Token Getter
    public String getAccessToken() {
        return accessToken;
    }

    // Refresh Token Getter
    public String getRefreshToken() {
        return refreshToken;
    }
}
