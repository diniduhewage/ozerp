package com.onezero.ozerp.appbase.transformer;

import com.onezero.ozerp.appbase.entity.Role;
import com.onezero.ozerp.appbase.entity.User;
import com.onezero.ozerp.appbase.entity.UserRole;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.appbase.dto.RoleDTO;
import com.onezero.ozerp.appbase.dto.UserDTO;
import com.onezero.ozerp.appbase.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTransformer extends AbstractTransformer<User, UserDTO> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleTransformer roleTransformer;

    @Override
    public UserDTO transformDomainToDTO(User domain) throws TransformerException {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(domain, dto);
        if (null != domain.getUserRoles()) {
            List<Role> roleList = new ArrayList<>();
            for (UserRole userRole : domain.getUserRoles()) {
                roleList.add(userRole.getRole());
            }
            dto.setRoleDTOs(roleTransformer.transformDomainToDTO(roleList));
        }
        return dto;
    }

    @Override
    public User transformDTOToDomain(UserDTO dto) throws TransformerException {
        User domain = new User();
        BeanUtils.copyProperties(dto, domain);

        domain.setPassword(passwordEncoder.encode(dto.getPassword()));
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
        domain.setAccountCreationDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
        domain.setModifiedDate(CommonUtils.timeStampGenerator());
        List<UserRole> userRoleList = new ArrayList<>();
        for (RoleDTO roleDTO : dto.getRoleDTOs()) {
            UserRole userRole = new UserRole();
            userRole.setRole(roleRepository.findById(roleDTO.getId()).get());
            userRole.setCreatedDate(null != roleDTO.getCreatedDate() ? roleDTO.getCreatedDate() : CommonUtils.timeStampGenerator());
            userRole.setModifiedDate(CommonUtils.timeStampGenerator());
            userRoleList.add(userRole);
        }
        domain.setUserRoles(userRoleList);
        return domain;
    }


}