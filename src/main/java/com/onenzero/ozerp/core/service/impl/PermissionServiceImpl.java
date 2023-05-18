package com.onenzero.ozerp.core.service.impl;

import com.onenzero.ozerp.core.constant.MessageConstants;
import com.onenzero.ozerp.core.dto.PermissionDTO;
import com.onenzero.ozerp.core.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.core.entity.Permission;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.repository.ActionRepository;
import com.onenzero.ozerp.core.repository.ComponentRepository;
import com.onenzero.ozerp.core.repository.PermissionRepository;
import com.onenzero.ozerp.core.repository.RolePermissionRepository;
import com.onenzero.ozerp.core.service.PermissionService;
import com.onenzero.ozerp.core.transformer.PermissionTransformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Deprecated
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    private final PermissionRepository permissionRepository;
    private final PermissionTransformer permissionTransformer;
    private final ActionRepository actionRepository;
    private final ComponentRepository componentRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public PermissionDTO savePermission(PermissionDTO permissionDTO) throws TransformerException {

        if (!actionRepository.existsById(permissionDTO.getActionDTO().getId())) {
            throw new NotFoundException("Action not found!!");
        }
        if (!componentRepository.existsById(permissionDTO.getComponentDTO().getId())) {
            throw new NotFoundException("Component not found!");
        }

        Permission permission = permissionTransformer.transformDTOToDomain(permissionDTO);
        return permissionTransformer.transformDomainToDTO(permissionRepository.saveAndFlush(permission));
    }

    @Override
    public PermissionDTO getPermissionById(Long id) throws NotFoundException, TransformerException {
        if (!permissionRepository.existsById(id)) {
            throw new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id);
        }
        return permissionTransformer.transformDomainToDTO(permissionRepository.findById(id).get());
    }

    @Override
    public List<PermissionDTO> getAllPermissions() throws TransformerException {
        return permissionTransformer.transformDomainToDTO(permissionRepository.findAll());
    }

    @Override
    public PermissionDeleteDTO deletePermissionByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) throws NotFoundException {
        if (Objects.isNull(permissionDeleteDTO) ||
                permissionDeleteDTO.getRoleId() == null ||
                permissionDeleteDTO.getPermissionId() == null) {
            throw new NotFoundException("Permission not Available");
        } else {
            rolePermissionRepository.deletePermissionForRole(permissionDeleteDTO.getPermissionId(), permissionDeleteDTO.getRoleId());
            permissionDeleteDTO.setIsPermissionDeleted(true);
            return permissionDeleteDTO;
        }
    }

}