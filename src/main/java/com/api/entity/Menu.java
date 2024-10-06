package com.api.entity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuCode; // 메뉴 코드
    private String menuName; // 메뉴 이름

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_roles",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Getter, Setter, Constructors
}