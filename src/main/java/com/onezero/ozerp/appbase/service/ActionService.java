package com.onezero.ozerp.appbase.service;


import com.onezero.ozerp.appbase.dto.ActionDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;


public interface ActionService {

    ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException;

    ActionDTO getActionById(Long id) throws NotFoundException, TransformerException;

    ResponseListDTO<ActionDTO> getAllActions(Integer page, Integer size, String sort) throws TransformerException;

}
