package com.onenzero.ozerp.appbase.dto.response;

import java.io.Serializable;
import java.util.List;

public class ResponseListDTO<T> extends ResultsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> payloadDto;
    private int count;

    public List<T> getPayloadDto() {
        return payloadDto;
    }

    public void setPayloadDto(List<T> payloadDto) {
        this.payloadDto = payloadDto;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
