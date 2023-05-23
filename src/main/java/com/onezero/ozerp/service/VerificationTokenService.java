package com.onezero.ozerp.service;

import com.onezero.ozerp.entity.VerificationToken;

public interface VerificationTokenService {
    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

}
