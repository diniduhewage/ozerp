package com.onenzero.ozerp.appbase.repository;

import com.onenzero.ozerp.appbase.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "delete from role_permission where permission_id=:permissionId and role_id=:roleId")
    void deletePermissionForRole(@Param("permissionId") Long permissionId, @Param("roleId") Long roleId);


    @Query(nativeQuery = true,
            value = "SELECT * FROM access_manager.role_permission   where permission_id=:permissionId and role_id=:roleId")
    RolePermission findPermissionForRole(@Param("permissionId") Long permissionID, @Param("roleId") Long roleId);
}
