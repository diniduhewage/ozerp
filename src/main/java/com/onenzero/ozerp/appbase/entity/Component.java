package com.onenzero.ozerp.appbase.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "component_tab")
public class Component {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String code;
	private Long createdDate;
	private Long modifiedDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "component")
    private List<Permission> permissions;

}

