package com.onenzero.ozerp.core.service;


import com.onenzero.ozerp.core.dto.LoginRequestDTO;
import com.onenzero.ozerp.core.dto.UserDTO;
import com.onenzero.ozerp.core.entity.PasswordResetToken;

public interface PasswordResetService {

    PasswordResetToken createToken(UserDTO userDTO);

    String validatePasswordResetTokenAndSavePassword(String token, LoginRequestDTO loginRequestDTO);
}
