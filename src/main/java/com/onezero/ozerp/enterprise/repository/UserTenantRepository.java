package com.onezero.ozerp.enterprise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onezero.ozerp.enterprise.entity.UserTenant;
import com.onezero.ozerp.enterprise.entity.UserTenant_PK;

@Repository
public interface UserTenantRepository extends JpaRepository<UserTenant, UserTenant_PK> {
	@Query(value = "SELECT * FROM UserTenant WHERE userId = :userId AND tenantId = :tenantId", nativeQuery = true)
	UserTenant findUserTenantByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);
	
	@Query(value = "SELECT COUNT(DISTINCT tenantId) FROM UserTenant WHERE userId = :userId", nativeQuery = true)
	Long countTenantsByUserId(@Param("userId") Long userId);


	@Query(value = "SELECT u.email, COUNT(DISTINCT ut.tenantId) " +
		       "FROM UserTenant ut " +
		       "INNER JOIN User u ON u.id = ut.userId " +
		       "WHERE u.contextUser = true " +
		       "GROUP BY u.email", nativeQuery = true)
		List<Object[]> getEmailAndTenantCountForContextUsers();

}
