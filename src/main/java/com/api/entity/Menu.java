package com.api.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "icon") // 메뉴 아이콘
    private String icon; // 메뉴 아이콘 (예: "dashboard", "table_view")

    @Column(name = "route") // 메뉴 경로
    private String route; // 메뉴 경로 (예: "/dashboard")

    // 하위 메뉴들을 위한 Set
    @OneToMany(mappedBy = "parent")
    private List<Menu> children = new ArrayList<>(); // 하위 메뉴들

    // 기본 생성자
    public Menu() {}

    // 생성자
    public Menu(String menuCode, CommonCode commonCode, Menu parent, String menuRole, String menuType, String icon, String route) {
        this.menuCode = menuCode;
        this.commonCode = commonCode;
        this.parent = parent;
        this.menuRole = menuRole;
        this.menuType = menuType;
        this.icon = icon;
        this.route = route;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
