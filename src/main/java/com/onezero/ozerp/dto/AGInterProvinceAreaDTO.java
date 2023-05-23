package com.onezero.ozerp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AGInterProvinceAreaDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Agriculture provincial id is required")
    private String agInterProvinceId;

    private String description;
}
