package com.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class) // 복합 키 클래스를 지정
public class UserRole {

    @Id
    @Column(name = "user_email")
    private String userEmail; // 사용자 이메일

    @Id
    @Column(name = "role_code")
    private String roleCode; // 역할 코드

    // 기본 생성자
    public UserRole() {}

    // 사용자와 역할을 연결하기 위한 생성자
    public UserRole(String userEmail, String roleCode) {
        this.userEmail = userEmail; // 사용자 이메일
        this.roleCode = roleCode; // 역할 코드
    }

    // Getter와 Setter
    public String getUserEmail() {
        return userEmail; // 사용자 이메일 반환
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail; // 사용자 이메일 설정
    }

    public String getRoleCode() {
        return roleCode; // 역할 코드 반환
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode; // 역할 코드 설정
    }
}
