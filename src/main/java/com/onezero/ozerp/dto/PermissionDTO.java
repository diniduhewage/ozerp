package com.onezero.ozerp.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;

    @NotNull(message = "Please provide componentDTO")
    @Valid
    private ComponentDTO componentDTO;
    @NotNull(message = "Please provide actionDTO")
    private ActionDTO actionDTO;
    private Long createdDate;
    private Long modifiedDate;
    @NotNull(message = "Please provide roleDTOList")
    private List<RoleDTO> roleDTOList;

}
