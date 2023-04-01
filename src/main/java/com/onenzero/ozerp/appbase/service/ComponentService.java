package com.onenzero.ozerp.appbase.service;

import com.onenzero.ozerp.appbase.dto.ComponentDTO;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;

import java.util.List;



public interface ComponentService {

	ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException;

	ComponentDTO getComponentById(Long id) throws NotFoundException, TransformerException;

	List<ComponentDTO> getAllComponents() throws TransformerException;

}