package com.onezero.ozerp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PasswordDTO implements Serializable {

    @NotEmpty(message = "Please provide email")
    private String email;
    @NotEmpty(message = "Please provide oldPassword")
    private String oldPassword;
    @NotEmpty(message = "Please provide newPassword")
    private String newPassword;

}

