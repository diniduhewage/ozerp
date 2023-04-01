package com.onenzero.ozerp.appbase.service;


import com.onenzero.ozerp.appbase.dto.PermissionDTO;
import com.onenzero.ozerp.appbase.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;

import java.util.List;

public interface PermissionService {

	PermissionDTO savePermission(PermissionDTO permissionDTO) throws TransformerException;

	PermissionDTO getPermissionById(Long id) throws NotFoundException, TransformerException;

	List<PermissionDTO> getAllPermissions() throws TransformerException;

	PermissionDeleteDTO deletePermissionByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) throws NotFoundException;
}
