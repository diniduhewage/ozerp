package com.onenzero.ozerp.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
