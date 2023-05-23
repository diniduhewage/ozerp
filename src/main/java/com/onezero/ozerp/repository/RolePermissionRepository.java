package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "delete from RolePermission where permissionId=:permissionId and roleId=:roleId")
    void deletePermissionForRole(@Param("permissionId") Long permissionId, @Param("roleId") Long roleId);


    @Query(nativeQuery = true,
            value = "SELECT * FROM RolePermission where permissionId=:permissionId and roleId=:roleId")
    RolePermission findPermissionForRole(@Param("permissionId") Long permissionID, @Param("roleId") Long roleId);


}
