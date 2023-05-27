package com.onezero.ozerp.transformer;

import com.onezero.ozerp.dto.PermissionDTO;
import com.onezero.ozerp.dto.RoleDTO;
import com.onezero.ozerp.entity.Permission;
import com.onezero.ozerp.entity.Role;
import com.onezero.ozerp.entity.RolePermission;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.PermissionRepository;
import com.onezero.ozerp.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleTransformer extends AbstractTransformer<Role, RoleDTO> {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionTransformer permissionTransformer;

    @Override
    public RoleDTO transformDomainToDTO(Role domain) throws TransformerException {
        RoleDTO dto = new RoleDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setCode(domain.getCode());
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        if (null != domain.getRolePermissions()) {
            List<Permission> permissionList = new ArrayList<>();
            for (RolePermission rolePermission : domain.getRolePermissions()) {
                permissionList.add(rolePermission.getPermission());
            }
            dto.setPermissionDTOs(permissionTransformer.transformDomainToDTO(permissionList));
        }

        return dto;
    }

    @Override
    public Role transformDTOToDomain(RoleDTO dto) throws TransformerException {
        Role domain = new Role();
        if (null != dto.getId()) {
            domain.setId(dto.getId());
        }
        domain.setName(dto.getName());
        domain.setCode(dto.getCode());
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
        domain.setModifiedDate(CommonUtils.timeStampGenerator());
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (PermissionDTO permissionDTO : dto.getPermissionDTOs()) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permissionRepository.findById(permissionDTO.getId()).get());
            rolePermission.setCreatedDate(null != permissionDTO.getCreatedDate() ? permissionDTO.getCreatedDate() : CommonUtils.timeStampGenerator());
            rolePermission.setModifiedDate(CommonUtils.timeStampGenerator());
            rolePermissionList.add(rolePermission);
        }
        domain.setRolePermissions(rolePermissionList);
        return domain;
    }

}