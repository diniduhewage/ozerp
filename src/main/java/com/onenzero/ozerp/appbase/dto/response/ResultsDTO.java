package com.onenzero.ozerp.appbase.dto.response;

import com.onenzero.ozerp.appbase.enums.ResultStatus;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ResultsDTO implements Serializable {

    @ApiModelProperty(
            notes = "Service message",
            position = 1)
    MessageDTO message;

    @ApiModelProperty(
            notes = "Result of the Response",
            position = 2)
    ResultStatus resultStatus;

    @ApiModelProperty(
            notes = "Http status of the Response",
            position = 3)
    HttpStatus httpStatus;

    @ApiModelProperty(
            notes = "Standard http code of the Response",
            position = 4)
    String httpCode;


    public ResultsDTO() {

    }

    public ResultsDTO(MessageDTO message, ResultStatus resultStatus, HttpStatus httpStatus, String httpCode) {
        this.message = message;
        this.resultStatus = resultStatus;
        this.httpStatus = httpStatus;
        this.httpCode = httpCode;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;

    }
}