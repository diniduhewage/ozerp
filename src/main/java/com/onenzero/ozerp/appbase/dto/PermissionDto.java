package com.onenzero.ozerp.appbase.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PermissionDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	@NotNull(message = "Please provide componentDTO")
	@Valid
    private ComponentDto componentDTO;
	@NotNull(message = "Please provide actionDTO")
	@Valid
    private ActionDto actionDTO;
    private Long createdDate;
	private Long modifiedDate;
    
}
