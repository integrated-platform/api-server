package com.api.repository;

import com.api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMenuRoleIn(List<String> roles);

    Optional<Menu> findByMenuCode(String menuCode);


}