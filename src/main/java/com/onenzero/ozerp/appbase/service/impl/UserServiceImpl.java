package com.onenzero.ozerp.appbase.service.impl;

import com.onenzero.ozerp.appbase.dto.*;
import com.onenzero.ozerp.appbase.entity.AppUser;
import com.onenzero.ozerp.appbase.error.exception.AuthorizationException;
import com.onenzero.ozerp.appbase.error.exception.BadRequestException;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.repository.RoleRepository;
import com.onenzero.ozerp.appbase.repository.UserRepository;
import com.onenzero.ozerp.appbase.service.RoleService;
import com.onenzero.ozerp.appbase.service.UserService;
import com.onenzero.ozerp.appbase.transformer.UserTransformer;
import com.onenzero.ozerp.appbase.util.JWTUtility;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserTransformer userTransformer;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	private RoleService roleService;

	@Override
	public UserDTO registerUser(UserDTO userDTO) throws TransformerException {
		if (findByUserName(userDTO.getUserName()) != null) {
			throw new BadRequestException("User already registered!");
		}
		for (RoleDTO roleDTO : userDTO.getRoleDTOs()) {
			if (!roleRepository.existsById(roleDTO.getId())) {
				throw new NotFoundException("Role not found!");
			}
		}
		AppUser user = userTransformer.transformDTOToDomain(userDTO);
		user.getUserRoles().forEach(userRole -> userRole.setUser(user));
		return userTransformer.transformDomainToDTO(userRepository.saveAndFlush(user));
	}


	@Override
	public UserDTO findByUserName(String userName) throws TransformerException {
		AppUser user = userRepository.findByUserName(userName);
		if (null != user) {
			return userTransformer.transformDomainToDTO(user);
		}
		return null;
	}


	@Override
	public String changePassword(PasswordDTO passwordDTO) {
		AppUser user = userRepository.findByUserName(passwordDTO.getEmail());
		if (null != user) {
			if (passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
				user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
				userRepository.saveAndFlush(user);
				return "Successfully changed the password.";
			}
			throw new BadRequestException("Invalid old password!");
		}
		throw new BadRequestException("Invalid user email!");
	}


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		AppUser user = userRepository.findByUserName(userName);
		
		if (null == user) {
			throw new UsernameNotFoundException("No user found");
		}
		

			try {
				return new org.springframework.security.core.userdetails.User(
						user.getUsername(), 
						user.getPassword(), 
						user.isEnabled(), 
						true, true, true, 
						getAuthority(user));
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			} catch (javax.xml.transform.TransformerException e) {
				throw new RuntimeException(e);
			}
		return null;
	}

	private Set<? extends GrantedAuthority> getAuthority(AppUser user) throws EntityNotFoundException, TransformerException, javax.xml.transform.TransformerException {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		RoleDTO roleDTO = roleService.getRoleById(user.getUserRoles().get(0).getRole().getId());
		
		roleDTO.getPermissionDTOList().forEach(permissionDTO -> {
    	  authorities.add(new SimpleGrantedAuthority(permissionDTO.getCode()));
      });
        return authorities;
	}

	public UserDetails getUserDetails(String userName, List<String> roleCodeList) {
		AppUser user = userRepository.findByUserName(userName);
		if (null == user) {
			throw new UsernameNotFoundException("No user found");
		}

		try {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.isEnabled(), true, true, true, getAuthority(user, roleCodeList));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException | javax.xml.transform.TransformerException e) {
			e.printStackTrace();
		}
		return null;

	}

	private Set<? extends GrantedAuthority> getAuthority(AppUser user, List<String> roleCodeList) throws NotFoundException, TransformerException, javax.xml.transform.TransformerException {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		List<String> permissionByRoleCodes = roleService.getPermissionByRoleCodes(roleCodeList);
		
		permissionByRoleCodes.forEach(permission -> {
			authorities.add(new SimpleGrantedAuthority(permission));
		});
        return authorities;
	}
	
	public JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) throws TransformerException {
		UserDTO findUserByEmail = findByUserName(loginRequestDTO.getUserName());
		if (findUserByEmail == null) {
			throw new AuthorizationException("Invalid Credentials!");
		}
		List<String> roleCodeByEmail = getRoleCodeByEmail(loginRequestDTO.getUserName());
		final UserDetails userDetails = getUserDetails(loginRequestDTO.getUserName(), roleCodeByEmail);
		if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword()) && findUserByEmail != null) {
//			ArrayList<String> roleLits = new ArrayList<>();
//			findUserByEmail.getRoleDTOs().forEach(role -> {
//				roleLits.add(role.getCode());
//			});
			final String token = jwtUtility.generateToken(userDetails, roleCodeByEmail);
			return new JWTResponseDTO(token);
		} else {
			throw new AuthorizationException("Invalid Credentials!");
		}
	}


	@Override
	public List<UserDTO> getAllUsers() throws TransformerException {
		return userTransformer.transformDomainToDTO(userRepository.findAll());
	}


	@Override
	public List<String> getRoleCodeByEmail(String userName) {
		return userRepository.findRoleCodesByUser(userName);
	}

}