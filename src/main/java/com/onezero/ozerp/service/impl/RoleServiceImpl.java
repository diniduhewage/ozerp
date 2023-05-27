package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.constant.EntityNotFoundConstant;
import com.onezero.ozerp.dto.PermissionDTO;
import com.onezero.ozerp.dto.RoleDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.Role;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.PermissionRepository;
import com.onezero.ozerp.repository.RoleRepository;
import com.onezero.ozerp.service.RoleService;
import com.onezero.ozerp.transformer.RoleTransformer;
import com.onezero.ozerp.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public RoleDTO saveRole(RoleDTO roleDTO) throws TransformerException {

        for (PermissionDTO permissionDTO : roleDTO.getPermissionDTOs()) {
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
            throw new NotFoundException(EntityNotFoundConstant.ROLE_NOT_FOUND + id);
        }
        return roleTransformer.transformDomainToDTO(roleRepository.findById(id).get());
    }

    @Override
    public ResponseListDTO<RoleDTO> getAllRoles(Integer page, Integer size, String sort) throws TransformerException {
        Page<Role> pageResponse = roleRepository.findAll(CommonUtils.createPageRequest(page, size, sort));
        List<RoleDTO> roleDTOList = roleTransformer.transformDomainToDTO(pageResponse.getContent());
        return new ResponseListDTO<>(roleDTOList, pageResponse.getTotalPages(), pageResponse.getTotalElements(),
                pageResponse.isLast(), pageResponse.getSize(), pageResponse.getNumber(), pageResponse.getSort(),
                pageResponse.getNumberOfElements());
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
    public List<String> getPermissionByRoleCodes(List<String> codeList) throws NotFoundException, TransformerException {
        return roleRepository.findPermissionByRoleCode(codeList);
    }


}
