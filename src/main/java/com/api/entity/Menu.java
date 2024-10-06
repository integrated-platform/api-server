package com.api.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @Column(name = "menu_code", nullable = false, unique = true)
    private String menuCode; // 메뉴 코드

    @OneToOne
    @JoinColumn(name = "common_code", referencedColumnName = "code", nullable = false) // 공통 코드와 조인
    private CommonCode commonCode; // 공통 코드

    @ManyToOne
    @JoinColumn(name = "parent_id") // 상위 메뉴 ID (자기 참조)
    private Menu parent; // 상위 메뉴

    @Column(name = "menu_role") // 메뉴에 대한 권한
    private String menuRole; // 메뉴 역할 (예: "ROLE_USER", "ROLE_ADMIN")

    @Column(name = "menu_type") // 메뉴 타입
    private String menuType; // 메뉴 타입 (예: "USER", "ADMIN")

    // 하위 메뉴들을 위한 Set
    @OneToMany(mappedBy = "parent")
    private Set<Menu> children = new HashSet<>(); // 하위 메뉴들

    // 기본 생성자
    public Menu() {}

    // 생성자
    public Menu(String menuCode, CommonCode commonCode, Menu parent, String menuRole, String menuType) {
        this.menuCode = menuCode;
        this.commonCode = commonCode;
        this.parent = parent;
        this.menuRole = menuRole;
        this.menuType = menuType;
    }

    // 공통 코드만 받는 생성자 (부모 메뉴는 null)
    public Menu(CommonCode commonCode) {
        this.commonCode = commonCode;
        this.menuCode = commonCode.getCode(); // 메뉴 코드를 공통 코드로 설정
        this.parent = null; // 상위 메뉴 없음
    }

    public Menu(CommonCode commonCode, Menu parent) {
        this.commonCode = commonCode;
        this.parent = parent;
    }


    // Getter, Setter
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }

    public void setCommonCode(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public String getMenuRole() {
        return menuRole;
    }

    public void setMenuRole(String menuRole) {
        this.menuRole = menuRole;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
    }
}
