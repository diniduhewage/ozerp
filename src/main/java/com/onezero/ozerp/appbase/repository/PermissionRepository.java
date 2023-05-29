package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appbase.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByCode(String code);

    boolean existsByCode(String code);
}