package com.onenzero.ozerp.appbase.service.impl;

import com.onenzero.ozerp.appbase.constant.EntityNotFoundConstant;
import com.onenzero.ozerp.appbase.dto.ActionDTO;
import com.onenzero.ozerp.appbase.entity.Action;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.repository.ActionRepository;
import com.onenzero.ozerp.appbase.service.ActionService;
import com.onenzero.ozerp.appbase.transformer.ActionTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActionServiceImpl implements ActionService {
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private ActionTransformer actionTransformer;
	
	private final Logger logger = LoggerFactory.getLogger(ActionService.class);

	@Override
	public ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException {
		Action action = actionTransformer.transformDTOToDomain(actionDTO);
		return actionTransformer.transformDomainToDTO(actionRepository.saveAndFlush(action));
	}

	@Override
	public ActionDTO getActionById(Long id) throws NotFoundException, TransformerException {
		if (!actionRepository.existsById(id)) {
			throw new NotFoundException(EntityNotFoundConstant.ACTION_NOT_FOUND + id);
		}
		return actionTransformer.transformDomainToDTO(actionRepository.findById(id).get());
	}

	@Override
	public List<ActionDTO> getAllActions() throws TransformerException {
		return actionTransformer.transformDomainToDTO(actionRepository.findAll());
	}

}