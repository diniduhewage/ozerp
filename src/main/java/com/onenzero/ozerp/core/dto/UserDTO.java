package com.onenzero.ozerp.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userName;
    @NotEmpty(message = "Please provide password")
    private String password;
    @NotEmpty(message = "Please provide matchingPassword")
    private String matchingPassword;
    @NotNull(message = "Please provide roleDTOs")
    private List<RoleDTO> roleDTOs;
    private Long createdDate;
    private Long modifiedDate;

}