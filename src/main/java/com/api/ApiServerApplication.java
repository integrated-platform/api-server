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
import org.springframework.context.ApplicationContext;

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
		// 공통 코드 초기화
		CommonCode userRoleCode = new CommonCode("ROLE_USER", "일반 사용자", "ROLE");
		CommonCode adminRoleCode = new CommonCode("ROLE_ADMIN", "관리자", "ROLE");

		// 메뉴 코드 초기화 (메뉴 코드와 역할 코드 일치)
		CommonCode userMenuCode = new CommonCode("ROLE_USER", "사용자 메뉴", "MENU");
		CommonCode adminMenuCode = new CommonCode("ROLE_ADMIN", "관리자 메뉴", "MENU");

		// 저장
		commonCodeRepository.save(userRoleCode);
		commonCodeRepository.save(adminRoleCode);
		commonCodeRepository.save(userMenuCode);
		commonCodeRepository.save(adminMenuCode);

		// 기본 역할 데이터 생성
		createRoleIfNotExists(userRoleCode);
		createRoleIfNotExists(adminRoleCode);
		createMenus();
	}

	private void createMenus() {
		// 상위 메뉴: 코딩 테스트
		CommonCode codingTestMenuCode = new CommonCode("CD000001", "코딩 테스트", "MENU");
		commonCodeRepository.save(codingTestMenuCode);

		// 하위 메뉴: 코딩 테스트 확인
		CommonCode codingTestCheckCode = new CommonCode("CD000002", "코딩 테스트 확인", "MENU");
		commonCodeRepository.save(codingTestCheckCode);

		// 하위 메뉴: 코딩 테스트 모든 결과
		CommonCode codingTestAllResultsCode = new CommonCode("CD000003", "코딩 테스트 모든 결과", "MENU");
		commonCodeRepository.save(codingTestAllResultsCode);

		// 하위 메뉴: 코딩 테스트 분석
		CommonCode codingTestAnalysisCode = new CommonCode("CD000004", "코딩 테스트 분석", "MENU");
		commonCodeRepository.save(codingTestAnalysisCode);

		// 메뉴 객체 생성
		Menu codingTestMenu = new Menu(codingTestMenuCode, null); // 상위 메뉴는 부모가 없음
		Menu codingTestCheckMenu = new Menu(codingTestCheckCode, codingTestMenu); // 하위 메뉴의 부모는 상위 메뉴
		Menu codingTestAllResultsMenu = new Menu(codingTestAllResultsCode, codingTestMenu); // 하위 메뉴의 부모는 상위 메뉴
		Menu codingTestAnalysisMenu = new Menu(codingTestAnalysisCode, codingTestMenu); // 하위 메뉴의 부모는 상위 메뉴

		// 권한 설정
		codingTestCheckMenu.setMenuRole("ROLE_USER"); // 사용자가 접근 가능한 하위 메뉴
		codingTestAllResultsMenu.setMenuRole("ROLE_ADMIN"); // 관리자가 접근 가능한 하위 메뉴
		codingTestAnalysisMenu.setMenuRole("ROLE_ADMIN"); // 관리자가 접근 가능한 하위 메뉴

		// 메뉴 저장
		menuRepository.save(codingTestMenu);
		menuRepository.save(codingTestCheckMenu);
		menuRepository.save(codingTestAllResultsMenu);
		menuRepository.save(codingTestAnalysisMenu);
	}

	private void createRoleIfNotExists(CommonCode commonCode) {
		if (roleRepository.findAll().stream().noneMatch(role -> role.getCommonCode().equals(commonCode))) {
			Role role = new Role(commonCode);
			roleRepository.save(role);
			System.out.println("Role created: " + commonCode.getCode());
		} else {
			System.out.println("Role already exists: " + commonCode.getCode());
		}
	}


}
