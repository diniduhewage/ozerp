package com.onenzero.ozerp.core.service;


import com.onenzero.ozerp.core.dto.PermissionDTO;
import com.onenzero.ozerp.core.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;

import java.util.List;

public interface PermissionService {

    PermissionDTO savePermission(PermissionDTO permissionDTO) throws TransformerException;

    PermissionDTO getPermissionById(Long id) throws NotFoundException, TransformerException;

    List<PermissionDTO> getAllPermissions() throws TransformerException;

    PermissionDeleteDTO deletePermissionByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) throws NotFoundException;
}
