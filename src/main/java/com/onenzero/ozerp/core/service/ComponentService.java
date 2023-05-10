package com.onenzero.ozerp.core.service;

import com.onenzero.ozerp.core.dto.ComponentDTO;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;

import java.util.List;



public interface ComponentService {

    ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException;

    ComponentDTO getComponentById(Long id) throws NotFoundException, TransformerException;

    List<ComponentDTO> getAllComponents() throws TransformerException;

}