package com.onenzero.ozerp.appbase.service;

import com.onenzero.ozerp.appbase.dto.ActionDTO;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;

import java.util.List;

public interface ActionService {

	ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException;

	ActionDTO getActionById(Long id) throws NotFoundException, TransformerException;

	List<ActionDTO> getAllActions() throws TransformerException;

}
