package com.oneszeros.accessmanager;

import com.onezero.ozerp.dto.UserDTO;
import com.onezero.ozerp.entity.PasswordResetToken;
import com.onezero.ozerp.entity.User;
import com.onezero.ozerp.repository.PasswordResetRepository;
import com.onezero.ozerp.service.impl.PasswordResetServiceImpl;
import com.onezero.ozerp.util.SaasUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetServiceImplTest {

    @Mock
    private PasswordResetRepository passwordResetRepository;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetService;

    @Test
    public void testCreateTokenWithExistingPasswordResetToken() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        String token = "aa2b722e-df04-41b0-b7a4-3880ddb0de96";
        PasswordResetToken existingToken = new PasswordResetToken(new User(1L), token);
        existingToken.setExpirationTime(SaasUtil.timeStampGenerator() - 1);

        Mockito.when(passwordResetRepository.findByUserId(1L)).thenReturn(existingToken);
        Mockito.when(passwordResetRepository.saveAndFlush(existingToken)).thenReturn(existingToken);

        PasswordResetToken newToken = passwordResetService.createToken(userDTO);

        Mockito.verify(passwordResetRepository, Mockito.times(1)).saveAndFlush(existingToken);
        assertNotEquals(SaasUtil.timeStampGenerator() - 1, newToken.getExpirationTime());
    }

}
