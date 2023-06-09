package com.onezero.ozerp.enterprise.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserTenant_PK implements Serializable {

	private static final long serialVersionUID = -5771150208058090708L;
	private Long userId;
	private Long tenantId;
}
