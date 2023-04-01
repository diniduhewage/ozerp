package com.onenzero.ozerp.appbase.dto.response;

import com.onenzero.ozerp.appbase.enums.ResultStatus;
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

    public void setPayload(T payloadDto) {
        this.payloadDto = payloadDto;
    }

    public T getPayload() {
        return payloadDto;
    }

}
