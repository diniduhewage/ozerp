package com.onezero.ozerp.dto;

import com.onezero.ozerp.error.ApiError;

public class ApiResponseDto {

    public boolean isSuccess;
    private String code;
    private String message;
    private Object payload;
    private ApiError apiError;

    public ApiResponseDto() {
        super();
    }

    public ApiResponseDto(boolean isSuccess, ApiError apiError) {
        super();
        this.isSuccess = isSuccess;
        this.apiError = apiError;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageDTO [code=");
        builder.append(code);
        builder.append(", message=");
        builder.append(message);
        builder.append(", isSuccess=");
        builder.append(isSuccess);
        builder.append(", payload=");
        builder.append(payload);
        builder.append(", apiError=");
        builder.append(apiError);
        builder.append("]");
        return builder.toString();
    }

}
