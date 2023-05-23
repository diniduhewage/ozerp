package com.onezero.ozerp.service;

import com.onezero.ozerp.dto.LoginRequestDTO;
import com.onezero.ozerp.dto.UserDTO;
import com.onezero.ozerp.entity.PasswordResetToken;

public interface PasswordResetService {


    PasswordResetToken createToken(UserDTO userDTO);

    String validatePasswordResetTokenAndSavePassword(String token, LoginRequestDTO loginRequestDTO);

}
