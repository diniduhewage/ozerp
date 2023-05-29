package com.onezero.ozerp.appbase.service.impl;

import com.onezero.ozerp.appbase.entity.User;
import com.onezero.ozerp.appbase.entity.VerificationToken;
import com.onezero.ozerp.appbase.error.exception.BadRequestException;
import com.onezero.ozerp.appbase.repository.UserRepository;
import com.onezero.ozerp.appbase.repository.VerificationTokenRepository;
import com.onezero.ozerp.appbase.service.VerificationTokenService;
import com.onezero.ozerp.appbase.util.CommonUtils;
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
        if (verificationToken.getExpirationTime() > CommonUtils.timeStampGenerator()) {
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
        verificationToken.setExpirationTime(CommonUtils.timeStampGenerator() + EXPIRATION_TIME);
        return verificationTokenRepository.saveAndFlush(verificationToken);
    }


}