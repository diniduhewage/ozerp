package com.onezero.ozerp.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onezero.ozerp.appservice.entity.IsoCurrency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

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
    @NotEmpty(message = "Company Id is required")
    private String companyId;
    private String description;
    private IsoCurrency accountingCurrency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CompanyAddressDTO> addresses;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdDate;

}