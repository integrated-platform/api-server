package com.api;

import com.api.entity.CommonCode;
import com.api.entity.Role;
import com.api.repository.CommonCodeRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 기본 공통 코드 데이터 생성
		CommonCode userRoleCode = new CommonCode("ROLE_USER", "일반 사용자", "ROLE");
		CommonCode adminRoleCode = new CommonCode("ROLE_ADMIN", "관리자", "ROLE");

		commonCodeRepository.save(userRoleCode);
		commonCodeRepository.save(adminRoleCode);

		// 기본 역할 데이터 생성
		createRoleIfNotExists(userRoleCode);
		createRoleIfNotExists(adminRoleCode);
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
