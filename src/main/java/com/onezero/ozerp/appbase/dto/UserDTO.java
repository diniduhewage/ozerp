package com.onezero.ozerp.appbase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserDTO implements Serializable {

    private Long id;
    @NotEmpty(message = "Please provide firstName")
    private String firstName;
    @NotEmpty(message = "Please provide lastName")
    private String lastName;
    @NotEmpty(message = "Please provide email")
    private String email;
    @NotEmpty(message = "Please provide password")
    private String password;
    @NotEmpty(message = "Please provide matchingPassword")
    private String matchingPassword;
    @NotNull(message = "Please provide roleDTOs")
    private List<RoleDTO> roleDTOs;
    private Long createdDate;
    private Long modifiedDate;
    private boolean enabled;
    private String phone;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String nationality;
    private String userType;
    private String username;
    private String status;
    private Long lastLoginDate;
    private Long lastLogoutDate;
    private Long accountCreationDate;
    private Long accountExpirationDate;
    private byte[] profilePicture;
    private String securityQuestion1;
    private String securityAnswer1;
    private String securityQuestion2;
    private String securityAnswer2;
    private boolean twoFactorAuthentication;
    private Long lastTwoFactorAuthenticationDate;
    private String preferredLanguage;
    private String timezone;
    private boolean notifications;
    private String notificationSettings;


}