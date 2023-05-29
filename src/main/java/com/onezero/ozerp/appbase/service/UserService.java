package com.onezero.ozerp.appbase.service;

import com.onezero.ozerp.appbase.dto.JWTResponseDTO;
import com.onezero.ozerp.appbase.dto.LoginRequestDTO;
import com.onezero.ozerp.appbase.dto.PasswordDTO;
import com.onezero.ozerp.appbase.dto.UserDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.TransformerException;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO) throws TransformerException;

    UserDTO findUserByEmail(String email) throws TransformerException;

    String changePassword(PasswordDTO passwordDTO);

    List<String> getRoleCodeByEmail(String email);

    JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) throws TransformerException;

    ResponseListDTO<UserDTO> getAllUsers(Integer page, Integer size, String sort) throws TransformerException;


}