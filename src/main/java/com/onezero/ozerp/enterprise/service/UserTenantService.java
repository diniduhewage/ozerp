package com.onezero.ozerp.enterprise.service;

import java.util.List;

import com.onezero.ozerp.appbase.error.exception.OzErpRuntimeException;
import com.onezero.ozerp.enterprise.dto.UserTenantDTO;


public interface UserTenantService {
	
 UserTenantDTO save(UserTenantDTO userTenantDTO) throws OzErpRuntimeException;

 UserTenantDTO update(UserTenantDTO userTenantDTO, Long id) throws OzErpRuntimeException;

 UserTenantDTO delete(Long id) throws OzErpRuntimeException;

 UserTenantDTO getById(Long id); 

 List<UserTenantDTO> getAll();
}
