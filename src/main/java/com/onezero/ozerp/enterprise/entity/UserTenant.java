package com.onezero.ozerp.enterprise.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.onezero.ozerp.appbase.entity.User;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class UserTenant implements Serializable {


	private static final long serialVersionUID = -3761006130391292260L;

	@EmbeddedId
    private
    UserTenant_PK userTenant_PK;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="tenantId", referencedColumnName = "id", insertable = false, updatable = false)
    private Tenant tenant;
    private Long createdDate;
    private Long modifiedDate;


    
}
