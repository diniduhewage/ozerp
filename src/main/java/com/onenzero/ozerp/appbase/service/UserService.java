package com.onenzero.ozerp.appbase.service;


import com.onenzero.ozerp.appbase.dto.JWTResponseDTO;
import com.onenzero.ozerp.appbase.dto.LoginRequestDTO;
import com.onenzero.ozerp.appbase.dto.PasswordDTO;
import com.onenzero.ozerp.appbase.dto.UserDTO;

import javax.xml.transform.TransformerException;
import java.util.List;

public interface UserService {
 
	UserDTO registerUser(UserDTO userDTO) throws TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;

	UserDTO findByUserName(String email) throws TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;

	String changePassword(PasswordDTO passwordDTO);
	
	List<String> getRoleCodeByEmail(String email);

	JWTResponseDTO getToken(LoginRequestDTO loginRequestDTO) throws TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;

	List<UserDTO> getAllUsers() throws TransformerException, com.onenzero.ozerp.appbase.error.exception.TransformerException;

 
}