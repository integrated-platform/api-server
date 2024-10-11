package com.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MenuDTO {
    private String menuCode;  // 메뉴 코드
    private String name;      // 메뉴 이름
    private String route;     // 메뉴 경로
    private String type;      // 메뉴 유형
    private String icon;      // 메뉴 아이콘
    private List<MenuDTO> children; // 하위 메뉴 목록

    // 생성자, 게터, 세터
}
