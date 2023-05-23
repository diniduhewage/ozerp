package com.onezero.ozerp.service;

import com.onezero.ozerp.dto.EmailDto;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDto emailDto);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDto emailDto);
}
