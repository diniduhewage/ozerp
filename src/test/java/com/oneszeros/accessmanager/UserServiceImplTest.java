package com.oneszeros.accessmanager;

import com.onezero.ozerp.appbase.dto.RoleDTO;
import com.onezero.ozerp.appbase.dto.UserDTO;
import com.onezero.ozerp.appbase.entity.Role;
import com.onezero.ozerp.appbase.entity.User;
import com.onezero.ozerp.appbase.entity.UserRole;
import com.onezero.ozerp.appbase.error.exception.BadRequestException;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.repository.RoleRepository;
import com.onezero.ozerp.appbase.repository.UserRepository;
import com.onezero.ozerp.appbase.service.RoleService;
import com.onezero.ozerp.appbase.service.impl.UserServiceImpl;
import com.onezero.ozerp.appbase.transformer.UserTransformer;
import com.onezero.ozerp.appbase.util.JWTUtility;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserTransformer userTransformer;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtility jwtUtility;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO = null;
    private User user = null;
    private RoleDTO roleDTO = null;

    @Before
    public void setUp() throws TransformerException {
        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setCode("ROLE_USER");
        userDTO.setRoleDTOs(Collections.singletonList(roleDTO));

        user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Role role = new Role();
        role.setId(1L);
        role.setCode("ROLE_USER");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        user.setUserRoles(Collections.singletonList(userRole));

        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        Mockito.when(userTransformer.transformDTOToDomain(userDTO)).thenReturn(user);
        Mockito.when(userTransformer.transformDomainToDTO(user)).thenReturn(userDTO);
        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);

    }

    @Test
    public void testRegisterUser_withNewUser_shouldReturnUserDTO() throws TransformerException {

        UserDTO result = userService.registerUser(userDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(userDTO.getEmail(), result.getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void testRegisterUser_withExistingUser_shouldThrowBadRequestException() throws TransformerException {

        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(user);

        userService.registerUser(userDTO);

    }


    @Test
    public void testRegisterUser_withValidNewUser_shouldReturnUserDTO() throws TransformerException {

        UserDTO result = userService.registerUser(userDTO);

        // assert the result
        Assert.assertNotNull(result);
        Assert.assertEquals(userDTO.getEmail(), result.getEmail());
        Assert.assertEquals(userDTO.getRoleDTOs().size(), result.getRoleDTOs().size());
        Assert.assertEquals(userDTO.getRoleDTOs().get(0).getId(), result.getRoleDTOs().get(0).getId());
        Assert.assertEquals(userDTO.getRoleDTOs().get(0).getCode(), result.getRoleDTOs().get(0).getCode());

        // verify the repository calls
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(userDTO.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(Mockito.any(User.class));
    }

    @Test(expected = NotFoundException.class)
    public void testRegisterUser_withNonExistingRole_shouldThrowNotFoundException() throws TransformerException {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setCode("ROLE_USER");
        userDTO.setRoleDTOs(Collections.singletonList(roleDTO));

        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        Mockito.when(roleRepository.existsById(1L)).thenReturn(false);

        userService.registerUser(userDTO);

    }


}
