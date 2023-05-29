package com.onezero.ozerp.appbase.service;

import com.onezero.ozerp.appbase.dto.LoginRequestDTO;
import com.onezero.ozerp.appbase.dto.UserDTO;
import com.onezero.ozerp.appbase.entity.PasswordResetToken;

public interface PasswordResetService {


    PasswordResetToken createToken(UserDTO userDTO);

    String validatePasswordResetTokenAndSavePassword(String token, LoginRequestDTO loginRequestDTO);

}
