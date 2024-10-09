package com.api;

import com.api.entity.CommonCode;
import com.api.entity.Menu;
import com.api.entity.Role;
import com.api.repository.CommonCodeRepository;
import com.api.repository.MenuRepository;
import com.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ApiServerApplication implements CommandLineRunner {

	@Autowired
	private CommonCodeRepository commonCodeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MenuRepository menuRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createCommonCodes();
		createRoles();
		createMenus();
	}
	private void createCommonCodes() {
		List<CommonCode> commonCodes = Arrays.asList(
				new CommonCode("CR000001", "일반 사용자", "ROLE"),
				new CommonCode("CR000002", "관리자", "ROLE"),
				new CommonCode("CM000001", "코딩 테스트", "MENU"),
				new CommonCode("CM000002", "코딩 테스트 확인", "MENU"),
				new CommonCode("CM000003", "코딩 테스트 모든 결과", "MENU"),
				new CommonCode("CM000004", "코딩 테스트 분석", "MENU")
		);

		commonCodeRepository.saveAll(commonCodes);
	}

	private void createRoles() {
		List<Role> roles = Arrays.asList(
				new Role("R000001", new CommonCode("CR000001", "일반 사용자", "USER"), "1"), // ROLE_USER
				new Role("R000002", new CommonCode("CR000002", "관리자", "ADMIN"), "2") // ROLE_ADMIN
		);

		roleRepository.saveAll(roles);
	}

	private void createMenus() {
		List<Menu> menus = Arrays.asList(
				new Menu("M000001", new CommonCode("CM000001", "코딩 테스트", "MENU"), null, "ROLE_USER", "USER", "code_test_icon", "/coding-test"), // 메뉴: 코딩 테스트
				new Menu("M000002", new CommonCode("CM000002", "코딩 테스트 확인", "MENU"), new Menu("M000001", new CommonCode("CM000001", "코딩 테스트", "MENU"), null, "ROLE_USER", "USER", "code_check_icon", "/coding-test/check"), "ROLE_USER", "USER", "code_check_icon", "/coding-test/check"), // 메뉴: 코딩 테스트 확인
				new Menu("M000003", new CommonCode("CM000003", "코딩 테스트 모든 결과", "MENU"), new Menu("M000001", new CommonCode("CM000001", "코딩 테스트", "MENU"), null, "ROLE_ADMIN", "USER", "all_results_icon", "/coding-test/results"), "ROLE_ADMIN", "ADMIN", "all_results_icon", "/coding-test/results"), // 메뉴: 코딩 테스트 모든 결과
				new Menu("M000004", new CommonCode("CM000004", "코딩 테스트 분석", "MENU"), new Menu("M000001", new CommonCode("CM000001", "코딩 테스트", "MENU"), null, "ROLE_ADMIN", "USER", "analysis_icon", "/coding-test/analyze"), "ROLE_ADMIN", "ADMIN", "analysis_icon", "/coding-test/analyze") // 메뉴: 코딩 테스트 분석
		);

		menuRepository.saveAll(menus);
	}



}
