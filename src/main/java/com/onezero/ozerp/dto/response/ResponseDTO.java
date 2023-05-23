package com.onezero.ozerp.dto.response;

import com.onezero.ozerp.enums.ResultStatus;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


public class ResponseDTO<T> extends ResultsDTO implements Serializable {

    private T payloadDto;

    public ResponseDTO() {
    }

    public ResponseDTO(T payloadDto) {
        this.payloadDto = payloadDto;
    }

    public ResponseDTO(MessageDTO message, ResultStatus resultStatus, HttpStatus httpStatus, String httpCode,
                       T payloadDto) {
        super(message, resultStatus, httpStatus, httpCode);
        this.payloadDto = payloadDto;
    }

    public static ResponseDTO<?> response(ResponseDTO<?> response) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }

    public static ResponseDTO<?> response(ResponseDTO<?> response, HttpStatus httpStatus) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        if (httpStatus == null) {
            response.setHttpStatus(HttpStatus.OK);
        } else {
            response.setHttpStatus(httpStatus);
        }
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }

    public T getPayload() {
        return payloadDto;
    }

    public void setPayload(T payloadDto) {
        this.payloadDto = payloadDto;
    }
}
