package com.onezero.ozerp.enterprise.dto;



import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTenantDTO {
	
	@NotNull(message = "userId Id is required")
	private Long userId;
	@NotNull(message = "tenantId Id is required")
	private Long tenantId;
	private Long createdDate;
	private Long modifiedDate;
}
