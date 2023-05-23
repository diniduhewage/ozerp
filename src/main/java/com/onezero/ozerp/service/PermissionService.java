package com.onezero.ozerp.service;


import com.onezero.ozerp.dto.PermissionDTO;
import com.onezero.ozerp.dto.PermissionDeleteDTO;
import com.onezero.ozerp.dto.PermissionManageDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;

import java.util.List;

public interface PermissionService {

    List<PermissionDTO> savePermission(PermissionManageDTO permissionManageDTO) throws TransformerException;

    PermissionDTO getPermissionById(Long id) throws NotFoundException, TransformerException;

    ResponseListDTO<PermissionDTO> getAllPermissions(Integer page, Integer size, String sort) throws TransformerException;

    PermissionDeleteDTO deletePermissionByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) throws NotFoundException;
}
