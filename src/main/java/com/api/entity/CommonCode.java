package com.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "common_codes")
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 공통 코드 ID

    private String code; // 코드 값
    private String name; // 코드 이름

    private String type; // 코드 타입 (예: ROLE, MENU 등)

    // 기본 생성자
    public CommonCode() {}

    // 생성자
    public CommonCode(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
