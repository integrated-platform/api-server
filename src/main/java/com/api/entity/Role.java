package com.api.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_code", nullable = false) // 역할 코드를 고유 식별자로 설정
    private String roleCode; // 고유 역할 코드

    @OneToOne
    @JoinColumn(name = "common_code", referencedColumnName = "code", nullable = false) // 공통 코드와 조인
    private CommonCode commonCode; // 공통 코드 (이름, 타입 등 포함)

    @Column(name = "access_level", nullable = false) // 접근 수준 추가
    private String accessLevel; // 역할의 유형

    @Column(name = "role", nullable = false) // 접근 수준 추가
    private String role; // 역할의 유형

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_code"), // 역할 코드와 매핑
            inverseJoinColumns = @JoinColumn(name = "user_email")
    )
    private Set<User> users = new HashSet<>(); // 이 권한을 가진 사용자들

    // 기본 생성자
    public Role() {}

    // 생성자
    public Role(String roleCode, CommonCode commonCode, String accessLevel , String role) {
        this.roleCode = roleCode;
        this.commonCode = commonCode; // 공통 코드를 설정
        this.accessLevel = accessLevel; // 접근 수준 설정
        this.role = role; // 접근 수준 설정
    }

    // Getter, Setter
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }

    public void setCommonCode(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
