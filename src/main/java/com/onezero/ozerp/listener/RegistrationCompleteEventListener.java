package com.onezero.ozerp.listener;

import com.onezero.ozerp.dto.EmailDto;
import com.onezero.ozerp.dto.UserDTO;
import com.onezero.ozerp.entity.User;
import com.onezero.ozerp.entity.VerificationToken;
import com.onezero.ozerp.event.RegistrationCompleteEvent;
import com.onezero.ozerp.service.EmailService;
import com.onezero.ozerp.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the User with the link
        UserDTO userDTO = event.getUserDTO();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(new User(userDTO.getId()), token);
        verificationToken = tokenService.saveVerificationToken(verificationToken);

        //Send mail to User
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        log.info("Click the link to verify your account: {}", url);

        EmailDto emailDto = new EmailDto();
        emailDto.setSubject("verify your account");
        emailDto.setRecipient(userDTO.getEmail());
        emailDto.setMsgBody("Click the link to verify your account:" + url);

        emailService.sendSimpleMail(emailDto);


    }

}