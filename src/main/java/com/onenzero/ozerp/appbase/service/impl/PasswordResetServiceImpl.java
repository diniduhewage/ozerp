package com.onenzero.ozerp.appbase.service.impl;

import com.onenzero.ozerp.appbase.dto.LoginRequestDTO;
import com.onenzero.ozerp.appbase.dto.UserDTO;
import com.onenzero.ozerp.appbase.entity.AppUser;
import com.onenzero.ozerp.appbase.entity.PasswordResetToken;
import com.onenzero.ozerp.appbase.error.exception.BadRequestException;
import com.onenzero.ozerp.appbase.repository.PasswordResetRepository;
import com.onenzero.ozerp.appbase.repository.UserRepository;
import com.onenzero.ozerp.appbase.service.PasswordResetService;
import com.onenzero.ozerp.appbase.util.SaasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PasswordResetServiceImpl implements PasswordResetService {
	
	private static final Long EXPIRATION_TIME = 600000L;
	
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public PasswordResetToken createToken(UserDTO userDTO) {
		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = passwordResetRepository.findByUserId(userDTO.getId());
		
		if (null != passwordResetToken) {
			passwordResetToken.setToken(token);
			passwordResetToken.setExpirationTime(SaasUtil.timeStampGenerator() + EXPIRATION_TIME);
			return passwordResetRepository.saveAndFlush(passwordResetToken);
		}
		return passwordResetRepository.saveAndFlush(new PasswordResetToken(new AppUser(userDTO.getId()), token));
	}


	@Override
	public String validatePasswordResetTokenAndSavePassword(String token, LoginRequestDTO loginRequestDTO) {
		PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);
		if (null == passwordResetToken) {
			return "Invalid Token";
		}
		if (passwordResetToken.getExpirationTime() > SaasUtil.timeStampGenerator()) {
			AppUser user = passwordResetToken.getUser();
			user.setPassword(passwordEncoder.encode(loginRequestDTO.getPassword()));
			userRepository.saveAndFlush(user);
			passwordResetToken.setExpirationTime(SaasUtil.timeStampGenerator());
			passwordResetRepository.saveAndFlush(passwordResetToken);
			return "Password reset successfully...";
		}
		throw new BadRequestException("Token Expired");
	}

}