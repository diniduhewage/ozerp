package com.onezero.ozerp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Long createdDate;
    private Long modifiedDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "componentId", referencedColumnName = "id", nullable = false)
    private Component component;

    @ManyToOne
    @JoinColumn(name = "actionId", referencedColumnName = "id", nullable = false)
    private Action action;

    @OneToMany(mappedBy = "permission", cascade = {CascadeType.PERSIST})
    private List<RolePermission> rolePermissions = new ArrayList<>();
}