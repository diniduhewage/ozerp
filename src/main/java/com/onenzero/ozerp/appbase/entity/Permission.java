package com.onenzero.ozerp.appbase.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission_tab")
public class Permission implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	private Long createdDate;
	private Long modifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "componentId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPONENT"))
    private Component component;
	
	@ManyToOne
    @JoinColumn(name = "actionId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ACTION"))
    private Action action;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "permission")
    private List<RolePermission> rolePermissions;
}