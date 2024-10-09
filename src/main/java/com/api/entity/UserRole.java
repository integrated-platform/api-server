package com.api.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class) // 복합 키 클래스를 지정
public class UserRole {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email") // User 엔티티의 email 필드와 연결
    private User user; // 사용자 엔티티와 관계 설정

    @Id
    @ManyToOne
    @JoinColumn(name = "role_code", referencedColumnName = "role_code") // Role 엔티티의 roleCode 필드와 연결
    private Role role; // 역할 엔티티와 관계 설정

    // 기본 생성자
    public UserRole() {}

    // 사용자와 역할을 연결하기 위한 생성자
    public UserRole(User user, Role role) {
        this.user = user; // 사용자 설정
        this.role = role; // 역할 설정
    }

    // Getter와 Setter
    public User getUser() {
        return user; // 사용자 반환
    }

    public void setUser(User user) {
        this.user = user; // 사용자 설정
    }

    public Role getRole() {
        return role; // 역할 반환
    }

    public void setRole(Role role) {
        this.role = role; // 역할 설정
    }
}
