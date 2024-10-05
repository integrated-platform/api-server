package com.api.dto;

public class LoginRequest {
    private String email;  // 필드 이름을 email로 변경
    private String password;

    // 생성자
    public LoginRequest(String email, String password) {
        this.email = email;  // 생성자 매개변수도 email로 변경
        this.password = password;
    }

    // Getter 메서드
    public String getEmail() {
        return email;  // Getter 메서드 이름도 email로 변경
    }

    public String getPassword() {
        return password;
    }
}

