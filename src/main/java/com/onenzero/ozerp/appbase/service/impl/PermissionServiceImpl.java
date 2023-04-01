package com.onenzero.ozerp.appbase.service.impl;

import com.onenzero.ozerp.appbase.constant.EntityNotFoundConstant;
import com.onenzero.ozerp.appbase.dto.PermissionDTO;
import com.onenzero.ozerp.appbase.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.appbase.entity.Permission;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.repository.ActionRepository;
import com.onenzero.ozerp.appbase.repository.ComponentRepository;
import com.onenzero.ozerp.appbase.repository.PermissionRepository;
import com.onenzero.ozerp.appbase.repository.RolePermissionRepository;
import com.onenzero.ozerp.appbase.service.PermissionService;
import com.onenzero.ozerp.appbase.transformer.PermissionTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class PermissionServiceImpl implements PermissionService {
	
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
	
	private final Logger logger = LoggerFactory.getLogger(PermissionService.class);

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
			throw new NotFoundException(EntityNotFoundConstant.PERMISSION_NOT_FOUND + id);
		}
		return permissionTransformer.transformDomainToDTO(permissionRepository.findById(id).get());
	}

	@Override
	public List<PermissionDTO> getAllPermissions() throws TransformerException {
		return permissionTransformer.transformDomainToDTO(permissionRepository.findAll());
	}

	@Override
	public PermissionDeleteDTO deletePermissionByRoleAndPermission(PermissionDeleteDTO permissionDeleteDTO) throws NotFoundException {
		/*RolePermission rolePermission = rolePermissionRepository.
				findPermissionForRole(permissionDeleteDTO.getPermissionId(), permissionDeleteDTO.getRoleId());*/
		if (permissionDeleteDTO.getRoleId() == null || permissionDeleteDTO.getPermissionId() == null || Objects.isNull(permissionDeleteDTO))
		{
			throw new NotFoundException("Permission not Available");
		} else {
			rolePermissionRepository.deletePermissionForRole(permissionDeleteDTO.getPermissionId(), permissionDeleteDTO.getRoleId());
		//	permissionDeleteDTO.setPermission(rolePermission.getPermission().getCode());
			permissionDeleteDTO.setIsPermissionDeleted(true);
			return permissionDeleteDTO;
		}
	}

}