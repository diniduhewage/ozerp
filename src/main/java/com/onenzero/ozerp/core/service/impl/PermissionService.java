package com.onenzero.ozerp.core.service.impl;

import com.onenzero.ozerp.core.constant.MessageConstants;
import com.onenzero.ozerp.core.dto.PermissionDTO;
import com.onenzero.ozerp.core.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.entity.Permission;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.repository.PermissionRepository;
import com.onenzero.ozerp.core.repository.RolePermissionRepository;
import com.onenzero.ozerp.core.service.GenericService;
import com.onenzero.ozerp.core.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService implements GenericService<PermissionDTO, PermissionDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionService.class);
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        LOGGER.debug("Saving permission: " + permissionDTO);
        permissionDTO.setCreatedDate(CommonUtils.timeStampGenerator());
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO, permission);
        Permission savedPermission = permissionRepository.save(permission);
        PermissionDTO returnPermissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(savedPermission, returnPermissionDTO);
        return returnPermissionDTO;
    }

    @Override
    public PermissionDTO getById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id));
        PermissionDTO permissionDTO = new PermissionDTO();
        BeanUtils.copyProperties(permission, permissionDTO);
        return permissionDTO;
    }

    @Override
    public ResponseListDTO<PermissionDTO> getAll(Pageable pageable) {
        Page<Permission> pageResponse = permissionRepository.findAll(pageable);
        List<PermissionDTO> permissionDTOList = pageResponse.map(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(permission, permissionDTO);
            return permissionDTO;
        }).stream().collect(Collectors.toList());
        return new ResponseListDTO<>(permissionDTOList,
                pageResponse.getTotalPages(),
                pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(),
                pageResponse.getNumber(),
                pageResponse.getSort(),
                pageResponse.getNumberOfElements());
    }

    @Override
    public PermissionDTO update(Long id, PermissionDTO permissionDTO) {
        throw new UnsupportedOperationException();
    }

    public PermissionDeleteDTO deleteByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) {
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

    @Override
    public boolean delete(Long id) {
        if (permissionRepository.existsById(id)) {
            LOGGER.debug("Deleting permission for id: " + id);
            permissionRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id);
        }
    }
}
