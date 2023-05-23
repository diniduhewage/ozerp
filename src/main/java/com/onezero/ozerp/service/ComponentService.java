package com.onezero.ozerp.service;


import com.onezero.ozerp.dto.ComponentDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;


public interface ComponentService {

    ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException;

    ComponentDTO getComponentById(Long id) throws NotFoundException, TransformerException;

    ResponseListDTO<ComponentDTO> getAllComponents(Integer page, Integer size, String sort) throws TransformerException;

}