package com.onezero.ozerp.service;

import com.onezero.ozerp.dto.JWTResponseDTO;
import com.onezero.ozerp.dto.LoginRequestDTO;
import com.onezero.ozerp.dto.PasswordDTO;
import com.onezero.ozerp.dto.UserDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.error.exception.TransformerException;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO) throws TransformerException;

    UserDTO findUserByEmail(String email) throws TransformerException;

    String changePassword(PasswordDTO passwordDTO);

    List<String> getRoleCodeByEmail(String email);

    JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) throws TransformerException;

    ResponseListDTO<UserDTO> getAllUsers(Integer page, Integer size, String sort) throws TransformerException;


}