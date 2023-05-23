package com.onezero.ozerp.service;


import com.onezero.ozerp.dto.ActionDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;


public interface ActionService {

    ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException;

    ActionDTO getActionById(Long id) throws NotFoundException, TransformerException;

    ResponseListDTO<ActionDTO> getAllActions(Integer page, Integer size, String sort) throws TransformerException;

}
