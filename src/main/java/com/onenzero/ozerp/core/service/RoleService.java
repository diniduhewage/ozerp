package com.onenzero.ozerp.core.service;

import com.onenzero.ozerp.core.dto.RoleDTO;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;

import javax.xml.transform.TransformerException;
import java.util.List;

public interface RoleService {

    RoleDTO saveRole(RoleDTO roleDTO) throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

    RoleDTO getRoleById(Long id) throws EntityNotFoundException, TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

    RoleDTO getRoleByCode(String code) throws NotFoundException, TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

    List<String> getPermissionByRoleCodes(List<String> codeList) throws NotFoundException, TransformerException;

    List<RoleDTO> getAllRoles() throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;
}
