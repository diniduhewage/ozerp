package com.onenzero.ozerp.appbase.service.impl;

import com.onenzero.ozerp.appbase.constant.EntityNotFoundConstant;
import com.onenzero.ozerp.appbase.dto.PermissionDTO;
import com.onenzero.ozerp.appbase.dto.RoleDTO;
import com.onenzero.ozerp.appbase.entity.Role;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.repository.PermissionRepository;
import com.onenzero.ozerp.appbase.repository.RoleRepository;
import com.onenzero.ozerp.appbase.service.RoleService;
import com.onenzero.ozerp.appbase.transformer.RoleTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleTransformer roleTransformer;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	private final Logger logger = LoggerFactory.getLogger(RoleService.class);

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
			throw new NotFoundException(EntityNotFoundConstant.ROLE_NOT_FOUND + id);
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
