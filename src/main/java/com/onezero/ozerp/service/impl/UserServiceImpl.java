package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.dto.JWTResponseDTO;
import com.onezero.ozerp.dto.LoginRequestDTO;
import com.onezero.ozerp.dto.PasswordDTO;
import com.onezero.ozerp.dto.RoleDTO;
import com.onezero.ozerp.dto.UserDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.User;
import com.onezero.ozerp.error.exception.AuthorizationException;
import com.onezero.ozerp.error.exception.BadRequestException;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.RoleRepository;
import com.onezero.ozerp.repository.UserRepository;
import com.onezero.ozerp.service.RoleService;
import com.onezero.ozerp.service.UserService;
import com.onezero.ozerp.transformer.UserTransformer;
import com.onezero.ozerp.util.CommonUtils;
import com.onezero.ozerp.util.JWTUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        UserDTO findUserByEmail = findUserByEmail(userDTO.getEmail());
        if (findUserByEmail != null) {
            throw new BadRequestException("User already registered!");
        }
        for (RoleDTO roleDTO : userDTO.getRoleDTOs()) {
            if (!roleRepository.existsById(roleDTO.getId())) {
                throw new NotFoundException("Role not found!");
            }
        }
        User user = userTransformer.transformDTOToDomain(userDTO);
        user.getUserRoles().stream().forEach(userRole -> userRole.setUser(user));
        return userTransformer.transformDomainToDTO(userRepository.saveAndFlush(user));
    }


    @Override
    public UserDTO findUserByEmail(String email) throws TransformerException {
        User user = userRepository.findByEmail(email);
        if (null != user) {
            return userTransformer.transformDomainToDTO(user);
        }
        return null;
    }


    @Override
    public String changePassword(PasswordDTO passwordDTO) {
        User user = userRepository.findByEmail(passwordDTO.getEmail());
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (null == user) {
            throw new UsernameNotFoundException("No user found");
        }


        try {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.isEnabled(),
                    true, true, true,
                    getAuthority(user));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Set<? extends GrantedAuthority> getAuthority(User user) throws EntityNotFoundException, TransformerException {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        RoleDTO roleDTO = roleService.getRoleById(user.getUserRoles().get(0).getRole().getId());

        roleDTO.getPermissionDTOs().forEach(permissionDTO -> {
            authorities.add(new SimpleGrantedAuthority(permissionDTO.getCode()));
        });
        return authorities;
    }

    public UserDetails getUserDetails(String email, List<String> roleCodeList) {
        User user = userRepository.findByEmail(email);
        if (null == user) {
            throw new UsernameNotFoundException("No user found");
        }

        try {
            logger.info(email);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    user.isEnabled(), true, true, true, getAuthority(user, roleCodeList));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;

    }

    private Set<? extends GrantedAuthority> getAuthority(User user, List<String> roleCodeList) throws NotFoundException, TransformerException {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        List<String> permissionByRoleCodes = roleService.getPermissionByRoleCodes(roleCodeList);

        permissionByRoleCodes.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });
        return authorities;
    }

    public JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) throws TransformerException {
        UserDTO findUserByEmail = findUserByEmail(loginRequestDTO.getUserName());
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
            final String token = jwtUtility.generateToken(userDetails, roleCodeByEmail, findUserByEmail.isEnabled());
            return new JWTResponseDTO(token);
        } else {
            throw new AuthorizationException("Invalid Credentials!");
        }
    }


    @Override
    public ResponseListDTO<UserDTO> getAllUsers(Integer page, Integer size, String sort) throws TransformerException {
        Page<User> pageResponse = userRepository.findAll(CommonUtils.createPageRequest(page, size, sort));
        List<UserDTO> userDTOList = userTransformer.transformDomainToDTO(pageResponse.getContent());
        return new ResponseListDTO<>(userDTOList, pageResponse.getTotalPages(), pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(), pageResponse.getNumber(),
                pageResponse.getSort(), pageResponse.getNumberOfElements());
    }


    @Override
    public List<String> getRoleCodeByEmail(String email) {
        return userRepository.findRoleCodesByUser(email);
    }

}