package com.onenzero.ozerp.appbase.service;


import com.onenzero.ozerp.appbase.dto.LoginRequestDTO;
import com.onenzero.ozerp.appbase.dto.UserDTO;
import com.onenzero.ozerp.appbase.entity.PasswordResetToken;

public interface PasswordResetService {

	PasswordResetToken createToken(UserDTO userDTO);

	String validatePasswordResetTokenAndSavePassword(String token, LoginRequestDTO loginRequestDTO);
}
