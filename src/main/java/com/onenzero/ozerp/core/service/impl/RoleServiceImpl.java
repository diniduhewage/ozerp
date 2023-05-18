package com.onenzero.ozerp.core.service.impl;

import com.onenzero.ozerp.core.constant.MessageConstants;
import com.onenzero.ozerp.core.dto.PermissionDTO;
import com.onenzero.ozerp.core.dto.RoleDTO;
import com.onenzero.ozerp.core.entity.Role;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.repository.PermissionRepository;
import com.onenzero.ozerp.core.repository.RoleRepository;
import com.onenzero.ozerp.core.service.RoleService;
import com.onenzero.ozerp.core.transformer.RoleTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleTransformer roleTransformer;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) throws NotFoundException, TransformerException {

        for (PermissionDTO permissionDTO : roleDTO.getPermissionDTOList()) {
            if (!permissionRepository.existsById(permissionDTO.getId())) {
                throw new NotFoundException("Permission not found...");
            }
        }
        Role role = roleTransformer.transformDTOToDomain(roleDTO);
        role.getRolePermissions().stream().forEach(rolePermission -> rolePermission.setRole(role));
        return roleTransformer.transformDomainToDTO(roleRepository.saveAndFlush(role));
    }

    @Override
    public RoleDTO getRoleById(Long id) throws NotFoundException, TransformerException {
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id);
        }
        return roleTransformer.transformDomainToDTO(roleRepository.findById(id).get());
    }

    @Override
    public List<RoleDTO> getAllRoles() throws TransformerException {
        return roleTransformer.transformDomainToDTO(roleRepository.findAll());
    }

    @Override
    public RoleDTO getRoleByCode(String code) throws NotFoundException, TransformerException {
        Role findByCode = roleRepository.findByCode(code);
        if (findByCode == null) {
            throw new NotFoundException("Role not found");
        }
        return roleTransformer.transformDomainToDTO(findByCode);
    }

    @Override
    public List<String> getPermissionByRoleCodes(List<String> codeList) throws NotFoundException {
        return roleRepository.findPermissionByRoleCode(codeList);
    }


}
