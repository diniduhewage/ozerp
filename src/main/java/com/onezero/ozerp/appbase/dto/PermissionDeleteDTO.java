package com.onezero.ozerp.appbase.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PermissionDeleteDTO implements Serializable {
    private Long roleId;
    private Long permissionId;


    private String permission;
    private Boolean isPermissionDeleted;
}
