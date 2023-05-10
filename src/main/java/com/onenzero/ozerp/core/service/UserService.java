package com.onenzero.ozerp.core.service;


import com.onenzero.ozerp.core.dto.JWTResponseDTO;
import com.onenzero.ozerp.core.dto.LoginRequestDTO;
import com.onenzero.ozerp.core.dto.PasswordDTO;
import com.onenzero.ozerp.core.dto.UserDTO;

import javax.xml.transform.TransformerException;
import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO) throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

    UserDTO findByUserName(String email) throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

    String changePassword(PasswordDTO passwordDTO);

    List<String> getRoleCodeByEmail(String email);

    JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

    List<UserDTO> getAllUsers() throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException;

 
}