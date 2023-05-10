package com.onenzero.ozerp.core.listener;

import com.onenzero.ozerp.core.dto.UserDTO;
import com.onenzero.ozerp.core.entity.AppUser;
import com.onenzero.ozerp.core.entity.VerificationToken;
import com.onenzero.ozerp.core.event.RegistrationCompleteEvent;
import com.onenzero.ozerp.core.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>{

    @Autowired
    private VerificationTokenService tokenService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the User with the link
        UserDTO userDTO = event.getUserDTO();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(new AppUser(userDTO.getId()), token);
        verificationToken = tokenService.saveVerificationToken(verificationToken);

        //Send mail to User
        String url = event.getApplicationUrl()+"/verifyRegistration?token="+token;
        log.info("Click the link to verify your account: {}", url);
    }

}