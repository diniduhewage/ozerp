package com.onezero.ozerp.appbase.service;

import com.onezero.ozerp.appbase.entity.VerificationToken;

public interface VerificationTokenService {
    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

}
