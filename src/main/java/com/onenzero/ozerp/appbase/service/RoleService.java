package com.onenzero.ozerp.appbase.service;

import com.onenzero.ozerp.appbase.dto.RoleDTO;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;

import javax.xml.transform.TransformerException;
import java.util.List;

public interface RoleService {

	RoleDTO saveRole(RoleDTO roleDTO) throws TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;

	RoleDTO getRoleById(Long id) throws EntityNotFoundException, TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;
	
	RoleDTO getRoleByCode(String code) throws NotFoundException, TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;
	
	List<String> getPermissionByRoleCodes(List<String> codeList) throws NotFoundException, TransformerException;

	List<RoleDTO> getAllRoles() throws TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;
}
