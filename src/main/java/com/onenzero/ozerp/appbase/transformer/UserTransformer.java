package com.onenzero.ozerp.appbase.transformer;

import com.onenzero.ozerp.appbase.dto.RoleDTO;
import com.onenzero.ozerp.appbase.dto.UserDTO;
import com.onenzero.ozerp.appbase.entity.AppUser;
import com.onenzero.ozerp.appbase.entity.Role;
import com.onenzero.ozerp.appbase.entity.UserRole;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.repository.RoleRepository;
import com.onenzero.ozerp.appbase.util.SaasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTransformer extends AbstractTransformer<AppUser, UserDTO> {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleTransformer roleTransformer;

	@Override
	public UserDTO transformDomainToDTO(AppUser domain) throws TransformerException {
		UserDTO dto = new UserDTO();
		dto.setId(domain.getId());
		dto.setUserName(domain.getUsername());
		dto.setFirstName(domain.getFirstName());
		dto.setLastName(domain.getLastName());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		if (null != domain.getUserRoles()) {
			List<Role>  roleList = new ArrayList<>();
			for (UserRole userRole : domain.getUserRoles()) {
				roleList.add(userRole.getRole());
			}
			dto.setRoleDTOs(roleTransformer.transformDomainToDTO(roleList));
		}
		return dto;
	}

	@Override
	public AppUser transformDTOToDomain(UserDTO dto) throws TransformerException {
		AppUser domain = new AppUser();
		if (null != dto.getId()) {
			domain.setId(dto.getId());
		}
		domain.setFirstName(dto.getFirstName());
		domain.setLastName(dto.getLastName());
		domain.setUserName(dto.getUserName());
		domain.setPassword(passwordEncoder.encode(dto.getPassword()));
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
		domain.setModifiedDate(SaasUtil.timeStampGenerator());
		List<UserRole>  userRoleList = new ArrayList<>();
		for (RoleDTO roleDTO : dto.getRoleDTOs()) {
			UserRole userRole = new UserRole();
			userRole.setRole(roleRepository.findById(roleDTO.getId()).get());
			userRole.setCreatedDate(null != roleDTO.getCreatedDate() ? roleDTO.getCreatedDate() : SaasUtil.timeStampGenerator());
			userRole.setModifiedDate(SaasUtil.timeStampGenerator());
			userRoleList.add(userRole);
		}
		domain.setUserRoles(userRoleList);
		return domain;
	}
}