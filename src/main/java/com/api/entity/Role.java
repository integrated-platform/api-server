package com.api.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 권한 ID

    @OneToOne
    @JoinColumn(name = "common_code_id", referencedColumnName = "id")
    private CommonCode commonCode; // 공통 코드

    @Column(name = "role_code") // 이 필드가 roleCode와 매핑됨
    private String roleCode; // 역할 코드

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>(); // 이 권한을 가진 사용자들

    // 기본 생성자
    public Role() {}

    // 생성자
    public Role(CommonCode commonCode) {
        this.commonCode = commonCode;
        this.roleCode = commonCode.getCode(); // commonCode에서 역할 코드를 가져오기
    }

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }

    public void setCommonCode(CommonCode commonCode) {
        this.commonCode = commonCode;
        this.roleCode = commonCode.getCode(); // commonCode가 변경되면 roleCode도 업데이트
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
