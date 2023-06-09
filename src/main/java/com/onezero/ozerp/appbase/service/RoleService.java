package com.onezero.ozerp.appbase.service;

import com.onezero.ozerp.appbase.dto.RoleDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface RoleService {

    RoleDTO saveRole(RoleDTO roleDTO) throws TransformerException;

    RoleDTO getRoleById(Long id) throws EntityNotFoundException, TransformerException;

    RoleDTO getRoleByCode(String code) throws NotFoundException, TransformerException;

    List<String> getPermissionByRoleCodes(List<String> codeList) throws NotFoundException, TransformerException;

    ResponseListDTO<RoleDTO> getAllRoles(Integer page, Integer size, String sort) throws TransformerException;
}
