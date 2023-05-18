package com.onenzero.ozerp.core.service;


import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface GenericService<ResponseDto, RequestDto> {
    ResponseDto save(RequestDto requestDto);

    ResponseDto getById(Long id) throws EntityNotFoundException;

    ResponseListDTO<ResponseDto> getAll(Pageable pageable);

    ResponseDto update(Long id, RequestDto requestDto);

    boolean delete(Long id);
}
