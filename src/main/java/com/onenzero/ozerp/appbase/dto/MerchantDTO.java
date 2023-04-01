package com.onenzero.ozerp.appbase.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	@NotEmpty(message = "Please provide name")
	private String name;
	@NotEmpty(message = "Please provide code")
	private String code;
	private Long createdDate;
	private Long modifiedDate;

}
