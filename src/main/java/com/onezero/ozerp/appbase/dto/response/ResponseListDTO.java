package com.onezero.ozerp.appbase.dto.response;

import com.onezero.ozerp.appbase.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseListDTO<T> extends ResultsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> payloadDto;
    private int totalPages;
    private long totalElements;
    private boolean last;
    private int size;
    private int number;
    private Sort sort;
    private int numberOfElements;

    public static ResponseListDTO<?> generateResponse(ResponseListDTO<?> response) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }
}
