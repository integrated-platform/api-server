package com.api.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {
    private String userEmail;
    private String roleCode;

    public UserRoleId() {}

    public UserRoleId(String userEmail, String roleCode) {
        this.userEmail = userEmail;
        this.roleCode = roleCode;
    }

    // equals()와 hashCode() 메서드 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleId)) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(roleCode, that.roleCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, roleCode);
    }

    // Getter와 Setter
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
