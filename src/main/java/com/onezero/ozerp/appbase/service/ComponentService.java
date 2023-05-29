package com.onezero.ozerp.appbase.service;


import com.onezero.ozerp.appbase.dto.ComponentDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;


public interface ComponentService {

    ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException;

    ComponentDTO getComponentById(Long id) throws NotFoundException, TransformerException;

    ResponseListDTO<ComponentDTO> getAllComponents(Integer page, Integer size, String sort) throws TransformerException;

}