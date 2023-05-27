package com.onezero.ozerp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onezero.ozerp.entity.IsoCurrency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CompanyDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @NotEmpty(message = "Please Company Id")
    private String companyId;
    private String description;
    private IsoCurrency accountingCurrency;
    private UserDTO createdBy;
    private Long createdDate;

}