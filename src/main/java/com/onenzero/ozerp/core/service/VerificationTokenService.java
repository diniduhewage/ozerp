package com.onenzero.ozerp.core.service;


import com.onenzero.ozerp.core.entity.VerificationToken;

public interface VerificationTokenService {
    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

}
