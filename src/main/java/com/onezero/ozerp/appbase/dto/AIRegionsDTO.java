package com.onezero.ozerp.appbase.dto;

import com.onezero.ozerp.appbase.enums.RegionParentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIRegionsDTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Region id is required")
    private String regionId;
    private String description;
    @NotBlank(message = "parentValue id is required")
    private RegionParentType parentType;
    private int parentValue;
    @NotBlank(message = "AscRegionId id is required")
    private int ascRegionId;
}
