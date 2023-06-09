package com.onezero.ozerp.enterprise.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Tenant implements Serializable {
    private static final long serialVersionUID = -6587598428337384828L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantCode;
    private Boolean enabled;
    private String tenantName;

    @Column(unique = true)
    private String tenantApiKey;

    private Boolean isDelete;
    private Long createdDate;
    private Long modifiedDate;

    private String modifiedBy;
    private String createdBy;
    
    @OneToMany(mappedBy = "tenant")
    private List<UserTenant> userTenantList;

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", tenantCode='" + tenantCode + '\'' +
                ", enabled=" + enabled +
                ", tenantName='" + tenantName + '\'' +
                ", tenantApiKey='" + tenantApiKey + '\'' +
                ", isDelete=" + isDelete +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
