package com.onezero.ozerp.appbase.service;

import com.onezero.ozerp.appbase.dto.EmailDto;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDto emailDto);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDto emailDto);
}
