package com.onenzero.ozerp.appbase.repository;

import com.onenzero.ozerp.appbase.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
 
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByCode(String code);
    
	@Query(nativeQuery = true, value = "SELECT DISTINCT p.code FROM ((role_tab r INNER JOIN role_permission_tab rp "
			+ "ON r.id = rp.role_id) INNER JOIN permission_tab p ON p.id = rp.permission_id) "
			+ "WHERE r.code IN (:roleCodeList)")
	List<String> findPermissionByRoleCode(@Param("roleCodeList") List<String> roleCodeList);
}
