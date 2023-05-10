package com.onenzero.ozerp.core.dto;

import lombok.Value;

import java.util.List;
 
@Value
public class UserInfo {
    private String id, displayName, userName;
    private List<String> roles;
}