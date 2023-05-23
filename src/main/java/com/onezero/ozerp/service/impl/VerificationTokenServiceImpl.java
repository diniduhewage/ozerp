package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.entity.User;
import com.onezero.ozerp.entity.VerificationToken;
import com.onezero.ozerp.error.exception.BadRequestException;
import com.onezero.ozerp.repository.UserRepository;
import com.onezero.ozerp.repository.VerificationTokenRepository;
import com.onezero.ozerp.service.VerificationTokenService;
import com.onezero.ozerp.util.SaasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final Long EXPIRATION_TIME = 600000L;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    @Autowired
    private UserRepository userRepository;

    @Override
    public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
        return verificationTokenRepository.saveAndFlush(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (null == verificationToken) {
            throw new BadRequestException("Invalid Token!");
        }
        if (verificationToken.getExpirationTime() > SaasUtil.timeStampGenerator()) {
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.saveAndFlush(user);
            return "valid";
        }
        throw new BadRequestException("Token Expired!");
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        verificationToken.setExpirationTime(SaasUtil.timeStampGenerator() + EXPIRATION_TIME);
        return verificationTokenRepository.saveAndFlush(verificationToken);
    }


}