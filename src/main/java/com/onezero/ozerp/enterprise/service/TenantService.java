package com.onezero.ozerp.enterprise.service;

import com.onezero.ozerp.appbase.error.exception.OzErpRuntimeException;
import com.onezero.ozerp.enterprise.dto.TenantDTO;

import java.util.List;


public interface TenantService {
 boolean isValidTenant(String tenant);
 TenantDTO saveTenant(TenantDTO tenantDTO) throws OzErpRuntimeException;

 TenantDTO updateTenant(TenantDTO tenantDTO, Long id) throws OzErpRuntimeException;

 TenantDTO deleteTenant(Long id) throws OzErpRuntimeException;

 TenantDTO getTenant(Object id); 

 List<TenantDTO> getAllTenants();
}
