package com.api.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {

    private String user; // User 엔티티의 email 필드
    private String role; // Role 엔티티의 roleCode 필드

    // 기본 생성자
    public UserRoleId() {}

    public UserRoleId(String user, String role) {
        this.user = user;
        this.role = role;
    }

    // equals와 hashCode 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
