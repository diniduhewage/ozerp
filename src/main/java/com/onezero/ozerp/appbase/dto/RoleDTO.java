package com.onezero.ozerp.appbase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @NotEmpty(message = "Please provide name")
    private String name;
    @NotEmpty(message = "Please provide code")
    private String code;
    private Long createdDate;
    private Long modifiedDate;
    @NotNull(message = "Please provide permissionDTOs")
    private List<PermissionDTO> permissionDTOs;

}
