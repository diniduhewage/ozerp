package com.onenzero.ozerp.appbase.entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role_tab")
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long createdDate;
	private Long modifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER"))
    private AppUser user;
	
	@ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_2"))
    private Role role;
	
}
