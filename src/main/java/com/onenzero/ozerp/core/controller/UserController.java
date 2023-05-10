package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.JWTResponseDTO;
import com.onenzero.ozerp.core.dto.LoginRequestDTO;
import com.onenzero.ozerp.core.dto.PasswordDTO;
import com.onenzero.ozerp.core.dto.PasswordResetDTO;
import com.onenzero.ozerp.core.dto.UserDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.entity.PasswordResetToken;
import com.onenzero.ozerp.core.entity.VerificationToken;
import com.onenzero.ozerp.core.enums.ResultStatus;
import com.onenzero.ozerp.core.error.exception.BadRequestException;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.event.RegistrationCompleteEvent;
import com.onenzero.ozerp.core.service.PasswordResetService;
import com.onenzero.ozerp.core.service.UserService;
import com.onenzero.ozerp.core.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PasswordResetService passwordResetService;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@PostMapping("/register")
	public ResponseDTO<?> registerUser(@RequestBody @Valid UserDTO userDTO, final HttpServletRequest request) throws TransformerException, javax.xml.transform.TransformerException {
		ResponseDTO<UserDTO> response = new ResponseDTO<>();
		userDTO = userService.registerUser(userDTO);
		response.setPayload(userDTO);
		publisher.publishEvent(new RegistrationCompleteEvent(userDTO, getApplicationUrl(request)));
        return updateResponse(response);
	}

	@PostMapping("/changePassword")
	public ResponseDTO<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO) throws TransformerException {
		ResponseDTO<String> response = new ResponseDTO<>();
		response.setPayload(userService.changePassword(passwordDTO));
		return updateResponse(response);
	}

	@PostMapping("/login")
    public ResponseDTO<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception{
		ResponseDTO<JWTResponseDTO> response = new ResponseDTO<>();
		response.setPayload(userService.getToken(loginRequestDTO));
		return updateResponse(response);
    }

	@GetMapping("/users")
	public ResponseListDTO<?> getAllRoles() throws TransformerException, javax.xml.transform.TransformerException {

		List<UserDTO> userDTOList = userService.getAllUsers();
		ResponseListDTO<UserDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(userDTOList);
		response.setCount(userDTOList.size());
		return updateResponse(response);

	}

	@PostMapping("/resetPassword")
	public ResponseDTO<?> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO, HttpServletRequest request) throws TransformerException, javax.xml.transform.TransformerException {
		UserDTO userDTO = userService.findByUserName(passwordResetDTO.getUserName());
		ResponseDTO<String> response = new ResponseDTO<>();
		if (null != userDTO) {
			PasswordResetToken passwordResetToken = passwordResetService.createToken(userDTO);
			sendPasswordResetTokenMail(passwordResetToken, request);
			response.setPayload("Password resend link send to your mail!");
			return updateResponse(response);
		}
		throw new NotFoundException("User not found");
	}

	@PostMapping("/reset/password")
	public ResponseDTO<?> savePassword(@RequestParam String token, @RequestBody LoginRequestDTO loginRequestDTO) {
		ResponseDTO<String> response = new ResponseDTO<>();
		String result = passwordResetService.validatePasswordResetTokenAndSavePassword(token, loginRequestDTO);
		response.setPayload(result);
		return updateResponse(response);
	}

	@GetMapping("/verifyRegistration")
	public ResponseDTO<?> verifyRegistration(@RequestParam String token) {
		ResponseDTO<String> response = new ResponseDTO<>();
		String result = verificationTokenService.validateVerificationToken(token);
		if (result.equalsIgnoreCase("valid")) {
			response.setPayload("User Verified Successfully!");
			return updateResponse(response);
		}
		throw new BadRequestException("Bad User!");
	}

	@GetMapping("resendVerifyToken")
	public ResponseDTO<?> resendVerificationToken(@RequestParam String oldToken, HttpServletRequest request) {
		ResponseDTO<String> response = new ResponseDTO<>();
		VerificationToken verificationToken = verificationTokenService.generateNewVerificationToken(oldToken);
		resendVerificationTokenMail(verificationToken, request);
		response.setPayload("Verification mail sent");
		return updateResponse(response);
	}

	private String getApplicationUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}

	private void resendVerificationTokenMail(VerificationToken verificationToken, HttpServletRequest request) {
		log.info("Click the link to verify your account: {}",
				getApplicationUrl(request) + "/verifyRegistration?token=" + verificationToken.getToken());
	}

	private void sendPasswordResetTokenMail(PasswordResetToken passwordResetToken, HttpServletRequest request) {
		log.info("Click the link to reset your password: {}",
				getApplicationUrl(request) + "/savePassword?token=" + passwordResetToken.getToken());
	}


	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}

	private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}

}
