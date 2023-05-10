package com.onenzero.ozerp.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDto {

    private String userRole;
    private String username;
    private String password;
    private String token;
    private String status;
    private boolean isToken;
    private String bussinessId;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsToken() {
        return isToken;
    }

    public void setIsToken(boolean isToken) {
        this.isToken = isToken;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuthDto [userRole=");
        builder.append(userRole);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", token=");
        builder.append(token);
        builder.append(", status=");
        builder.append(status);
        builder.append(", isToken=");
        builder.append(isToken);
        builder.append(", bussinessId=");
        builder.append(bussinessId);
        builder.append("]");
        return builder.toString();
    }


}
