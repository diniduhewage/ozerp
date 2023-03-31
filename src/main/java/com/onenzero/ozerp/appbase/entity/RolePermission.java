package com.onenzero.ozerp.appbase.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role_permission_tab")
public class RolePermission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long createdDate;
	private Long modifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERMISSION"))
    private Permission permission;
	
	@ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_1"))
    private Role role;
}
