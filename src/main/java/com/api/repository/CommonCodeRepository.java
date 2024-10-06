package com.api.repository;

import com.api.entity.CommonCode;
import com.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
    // 필요한 경우 추가적인 쿼리 메서드를 정의할 수 있습니다.
    CommonCode findByCode(String code);
}