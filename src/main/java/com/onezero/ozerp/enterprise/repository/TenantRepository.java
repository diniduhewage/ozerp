package com.onezero.ozerp.enterprise.repository;

import com.onezero.ozerp.enterprise.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsTenantByTenantApiKey(String tenantApiKey);

    Optional<Tenant> findTenantByTenantApiKey(String apiKey);

    List<Tenant> findByEnabledTrueAndIsDeleteFalse();
    
    boolean existsTenantByTenantCode(String tenatCode);
    
    @Query(value = "SELECT tenantApiKey FROM Tenant WHERE id IN (:tenantIds) AND enabled = true AND isDelete = false", nativeQuery = true)
    List<String> findTenantApiKeysByIds(@Param("tenantIds") List<Long> tenantIds);

}
