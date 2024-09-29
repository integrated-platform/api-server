package com.api.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;

    // 기본 생성자
    public UserDTO() {}

    // 모든 필드를 포함한 생성자
    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter와 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
