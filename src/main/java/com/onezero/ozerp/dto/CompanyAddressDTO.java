package com.onezero.ozerp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onezero.ozerp.entity.Company;
import com.onezero.ozerp.entity.IsoCountry;
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
public class CompanyAddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Company company;
    private String address1;
    private String address2;
    private String city;
    private IsoCountry country;

}