package com.onezero.ozerp.enterprise.dto;



import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantDTO {
    private Long id;
    private String tenantCode;
    private Boolean enabled;
    @NotEmpty(message = "Please provide Tenant name")
    private String tenantName;
    private String tenantApiKey;
    private Boolean isDelete;
    private Long modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private Long createdDate;

    @Override
    public String toString() {
        return "TenantDTO{" +
                "id=" + id +
                ", tenantCode='" + tenantCode + '\'' +
                ", enabled=" + enabled +
                ", tenantName='" + tenantName + '\'' +
                ", tenantApiKey='" + tenantApiKey + '\'' +
                ", isDelete=" + isDelete +
                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
