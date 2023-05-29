package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appbase.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Role findByCode(String code);

    @Query(nativeQuery = true, value = "SELECT DISTINCT p.code FROM ((Role r INNER JOIN RolePermission rp "
            + "ON r.id = rp.roleId) INNER JOIN Permission p ON p.id = rp.permissionId) "
            + "WHERE r.code IN (:roleCodeList)")
    public List<String> findPermissionByRoleCode(@Param("roleCodeList") List<String> roleCodeList);

    @Query("SELECT r FROM Role r WHERE r.id NOT IN :roleIds")
    List<Role> findRolesNotIn(@Param("roleIds") List<Long> roleIds);

    boolean existsByCode(String code);
}
