package com.onezero.ozerp.appbase.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"code", "message"})
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 3253076720968896892L;
    private String message;
    private String code;
    private String locale = "en_GB";
    private String system;
    private String systemErrorCode;
    private String systemErrorMessage;
    private String language;
    private String hashcode;

    public MessageDTO() {
    }

    /**
     * @param message
     */
    public MessageDTO(String message) {
        this.message = message;
    }

    public MessageDTO(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public MessageDTO(String message, String code, String locale) {
        this.message = message;
        this.code = code;
        this.locale = locale;
    }

    public MessageDTO(String message, String code, String locale, String system, String systemErrorCode,
                      String systemErrorMessage, String language, String hashcode) {
        this.message = message;
        this.code = code;
        this.locale = locale;
        this.system = system;
        this.systemErrorCode = systemErrorCode;
        this.systemErrorMessage = systemErrorMessage;
        this.language = language;
        this.hashcode = hashcode;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemErrorCode() {
        return systemErrorCode;
    }

    public void setSystemErrorCode(String systemErrorCode) {
        this.systemErrorCode = systemErrorCode;
    }

    public String getSystemErrorMessage() {
        return systemErrorMessage;
    }

    public void setSystemErrorMessage(String systemErrorMessage) {
        this.systemErrorMessage = systemErrorMessage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageDTO [message=");
        builder.append(message);
        builder.append(", code=");
        builder.append(code);
        builder.append(", locale=");
        builder.append(locale);
        builder.append(", system=");
        builder.append(system);
        builder.append(", systemErrorCode=");
        builder.append(systemErrorCode);
        builder.append(", systemErrorMessage=");
        builder.append(systemErrorMessage);
        builder.append(", language=");
        builder.append(language);
        builder.append(", hashcode=");
        builder.append(hashcode);
        builder.append("]");
        return builder.toString();
    }

}