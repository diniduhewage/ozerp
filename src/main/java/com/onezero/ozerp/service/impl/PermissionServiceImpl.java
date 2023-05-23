package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.constant.EntityNotFoundConstatnt;
import com.onezero.ozerp.dto.ActionDTO;
import com.onezero.ozerp.dto.ComponentDTO;
import com.onezero.ozerp.dto.PermissionDTO;
import com.onezero.ozerp.dto.PermissionDeleteDTO;
import com.onezero.ozerp.dto.PermissionManageDTO;
import com.onezero.ozerp.dto.RoleDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.Action;
import com.onezero.ozerp.entity.Component;
import com.onezero.ozerp.entity.Permission;
import com.onezero.ozerp.entity.Role;
import com.onezero.ozerp.entity.RolePermission;
import com.onezero.ozerp.error.exception.BadRequestException;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.ActionRepository;
import com.onezero.ozerp.repository.ComponentRepository;
import com.onezero.ozerp.repository.PermissionRepository;
import com.onezero.ozerp.repository.RolePermissionRepository;
import com.onezero.ozerp.repository.RoleRepository;
import com.onezero.ozerp.service.PermissionService;
import com.onezero.ozerp.transformer.ComponentTransformer;
import com.onezero.ozerp.transformer.PermissionTransformer;
import com.onezero.ozerp.util.SaasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionTransformer permissionTransformer;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ComponentTransformer componentTransformer;

    @Override
    public List<PermissionDTO> savePermission(PermissionManageDTO permissionManageDTO) throws TransformerException {

        List<Permission> permissionList = new ArrayList<>();

        List<ComponentDTO> componentDTOList = permissionManageDTO.getComponentDTOList();
        List<Long> actionIds = componentDTOList.stream()
                .filter(componentDTO -> componentDTO.getActionDTOList() != null)
                .flatMap(componentDTO -> componentDTO.getActionDTOList().stream())
                .map(ActionDTO::getId)
                .distinct()
                .collect(Collectors.toList());
        if (actionIds == null || actionIds.isEmpty()) {
            throw new BadRequestException("Action list is empty or contains null values");
        }

        List<Action> actions = actionRepository.findAllById(actionIds);
        if (actions.size() != actionIds.size()) {
            throw new BadRequestException("Invalid action id found, check the request body!");
        }
        for (ComponentDTO componentDTO : componentDTOList) {
            Component component = componentRepository.findByCode(componentDTO.getCode());
            if (component == null) {
                component = componentTransformer.transformDTOToDomain(componentDTO);
                componentRepository.save(component);
            }
            List<ActionDTO> actionDTOList = componentDTO.getActionDTOList();
            for (ActionDTO actionDTO : actionDTOList) {
                Action action = actionRepository.findById(actionDTO.getId()).orElse(null);
                Permission permission = new Permission();
                permission.setComponent(component);
                permission.setAction(action);
                permission.setCode(action.getCode() + "_" + component.getCode());

                if (permissionRepository.findByCode(permission.getCode()) != null) {
                    throw new BadRequestException("Permission already registered!");
                }
                permission.setCreatedDate(SaasUtil.timeStampGenerator());
                permission.setModifiedDate(SaasUtil.timeStampGenerator());

                RoleDTO roleDTO = permissionManageDTO.getRoleDTO();
                Role role = roleRepository.findById(roleDTO.getId()).orElse(null);

                RolePermission rolePermission = new RolePermission();
                rolePermission.setCreatedDate(SaasUtil.timeStampGenerator());
                rolePermission.setModifiedDate(SaasUtil.timeStampGenerator());
                rolePermission.setPermission(permission);
                rolePermission.setRole(role);

                permission.getRolePermissions().add(rolePermission);
                permissionList.add(permission);
            }
        }
        return permissionTransformer.transformDomainToDTO(permissionRepository.saveAll(permissionList));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDTO getPermissionById(Long id) throws NotFoundException, TransformerException {
        if (!permissionRepository.existsById(id)) {
            throw new NotFoundException(EntityNotFoundConstatnt.PERMISSION_NOT_FOUND + id);
        }
        return permissionTransformer.transformDomainToDTO(permissionRepository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseListDTO<PermissionDTO> getAllPermissions(Integer page, Integer size, String sort) throws TransformerException {
        Page<Permission> pageResponse = permissionRepository.findAll(SaasUtil.createPageRequest(page, size, sort));
        List<PermissionDTO> permissionDTOList = permissionTransformer.transformDomainToDTO(pageResponse.getContent());

        return new ResponseListDTO<>(permissionDTOList, pageResponse.getTotalPages(), pageResponse.getTotalElements(),
                pageResponse.isLast(), pageResponse.getSize(), pageResponse.getNumber(), pageResponse.getSort(),
                pageResponse.getNumberOfElements());
    }

    @Override
    public PermissionDeleteDTO deletePermissionByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) throws NotFoundException {
        /*RolePermission rolePermission = rolePermissionRepository.
                findPermissionForRole(permissionDeleteDTO.getPermissionId(), permissionDeleteDTO.getRoleId());*/
        if (permissionDeleteDTO.getRoleId() == null || permissionDeleteDTO.getPermissionId() == null
                || Objects.isNull(permissionDeleteDTO)) {
            throw new NotFoundException("Permission not Available");
        } else {
            rolePermissionRepository.deletePermissionForRole(permissionDeleteDTO.getPermissionId(), permissionDeleteDTO.getRoleId());
			//	permissionDeleteDTO.setPermission(rolePermission.getPermission().getCode());
            permissionDeleteDTO.setIsPermissionDeleted(true);
            return permissionDeleteDTO;
        }
    }

}