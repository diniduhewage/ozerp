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
@Builder
@Entity
@Table(name = "action_tab")
public class Action implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String code;
	private Long createdDate;
	private Long modifiedDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "action")
    private List<Permission> permissions;
}