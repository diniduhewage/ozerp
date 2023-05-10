package com.onenzero.ozerp.core.repository;

import com.onenzero.ozerp.core.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {



}