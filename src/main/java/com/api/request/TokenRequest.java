package com.api.request;

public class TokenRequest {
    private String token;

    // 기본 생성자
    public TokenRequest() {}

    // 생성자
    public TokenRequest(String token) {
        this.token = token;
    }

    // Getter 및 Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
