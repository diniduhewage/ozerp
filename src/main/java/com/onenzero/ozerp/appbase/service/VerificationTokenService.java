package com.onenzero.ozerp.appbase.service;


import com.onenzero.ozerp.appbase.entity.VerificationToken;

public interface VerificationTokenService {
	VerificationToken saveVerificationToken(VerificationToken verificationToken);
	
	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldToken);

}
