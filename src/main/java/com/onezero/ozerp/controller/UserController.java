package com.onezero.ozerp.controller;

import com.onezero.ozerp.dto.EmailDto;
import com.onezero.ozerp.dto.JWTResponseDTO;
import com.onezero.ozerp.dto.LoginRequestDTO;
import com.onezero.ozerp.dto.PasswordDTO;
import com.onezero.ozerp.dto.PasswordResetDTO;
import com.onezero.ozerp.dto.UserDTO;
import com.onezero.ozerp.dto.response.ResponseDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.PasswordResetToken;
import com.onezero.ozerp.entity.VerificationToken;
import com.onezero.ozerp.error.exception.BadRequestException;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.event.RegistrationCompleteEvent;
import com.onezero.ozerp.service.EmailService;
import com.onezero.ozerp.service.PasswordResetService;
import com.onezero.ozerp.service.UserService;
import com.onezero.ozerp.service.VerificationTokenService;
import com.onezero.ozerp.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseDTO<?> registerUser(@RequestBody @Valid UserDTO userDTO, final HttpServletRequest request) throws TransformerException {
        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        userDTO = userService.registerUser(userDTO);
        response.setPayload(userDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(userDTO, getApplicationUrl(request)));
        return CommonUtils.updateResponse(response);
    }

    @PostMapping("/changePassword")
    public ResponseDTO<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO) throws TransformerException {
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setPayload(userService.changePassword(passwordDTO));
        return CommonUtils.updateResponse(response);
    }

    @PostMapping("/login")
    public ResponseDTO<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception {
        ResponseDTO<JWTResponseDTO> response = new ResponseDTO<>();
        response.setPayload(userService.getToken(loginRequestDTO));
        return CommonUtils.updateResponse(response);
    }

    @GetMapping("/users")
    public ResponseListDTO<?> getAllRoles(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(required = false) String sort) throws TransformerException {

        ResponseListDTO<UserDTO> response = userService.getAllUsers(page, size, sort);
        return CommonUtils.updateResponse(response);

    }

    @PostMapping("/resetPassword")
    public ResponseDTO<?> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO, HttpServletRequest request) throws TransformerException {
        UserDTO userDTO = userService.findUserByEmail(passwordResetDTO.getEmail());
        ResponseDTO<String> response = new ResponseDTO<>();
        if (null != userDTO) {
            PasswordResetToken passwordResetToken = passwordResetService.createToken(userDTO);
            sendPasswordResetTokenMail(passwordResetToken, request);
            response.setPayload("Password resend link send to your mail!");
            return CommonUtils.updateResponse(response);
        }
        throw new NotFoundException("User not found");
    }

    @PostMapping("/reset/password")
    public ResponseDTO<?> savePassword(@RequestParam String token, @RequestBody LoginRequestDTO loginRequestDTO) {
        ResponseDTO<String> response = new ResponseDTO<>();
        String result = passwordResetService.validatePasswordResetTokenAndSavePassword(token, loginRequestDTO);
        response.setPayload(result);
        return CommonUtils.updateResponse(response);
    }

    @GetMapping("/verifyRegistration")
    public ResponseDTO<?> verifyRegistration(@RequestParam String token) {
        ResponseDTO<String> response = new ResponseDTO<>();
        String result = verificationTokenService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            response.setPayload("User Verified Successfully!");
            return CommonUtils.updateResponse(response);
        }
        throw new BadRequestException("Bad User!");
    }

    @GetMapping("resendVerifyToken")
    public ResponseDTO<?> resendVerificationToken(@RequestParam String oldToken, HttpServletRequest request) {
        ResponseDTO<String> response = new ResponseDTO<>();
        VerificationToken verificationToken = verificationTokenService.generateNewVerificationToken(oldToken);
        resendVerificationTokenMail(verificationToken, request);
        response.setPayload("Verification mail sent");
        return CommonUtils.updateResponse(response);
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void resendVerificationTokenMail(VerificationToken verificationToken, HttpServletRequest request) {
        String url = getApplicationUrl(request) + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account: {}", url);
        EmailDto emailDto = new EmailDto();
        emailDto.setSubject("verify your account");
        emailDto.setRecipient(verificationToken.getUser().getEmail());
        emailDto.setMsgBody("Click the link to reset your password:" + url);

        emailService.sendSimpleMail(emailDto);
    }

    private void sendPasswordResetTokenMail(PasswordResetToken passwordResetToken, HttpServletRequest request) {
        String url = getApplicationUrl(request) + "/savePassword?token=" + passwordResetToken.getToken();
        log.info("Click the link to reset your password: {}", url);

        EmailDto emailDto = new EmailDto();
        emailDto.setSubject("Reset password");
        emailDto.setRecipient(passwordResetToken.getUser().getEmail());
        emailDto.setMsgBody("Click the link to reset your password:" + url);

        emailService.sendSimpleMail(emailDto);

    }

}
