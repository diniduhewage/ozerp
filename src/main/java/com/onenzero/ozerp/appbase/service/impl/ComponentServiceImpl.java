package com.onenzero.ozerp.appbase.service.impl;

import com.onenzero.ozerp.appbase.constant.EntityNotFoundConstant;
import com.onenzero.ozerp.appbase.dto.ComponentDTO;
import com.onenzero.ozerp.appbase.entity.Component;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.repository.ComponentRepository;
import com.onenzero.ozerp.appbase.service.ComponentService;
import com.onenzero.ozerp.appbase.transformer.ComponentTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComponentServiceImpl implements ComponentService {
	
	@Autowired
	private ComponentRepository componentRepository;
	
	@Autowired
	private ComponentTransformer componentTransformer;
	
	private final Logger logger = LoggerFactory.getLogger(ComponentService.class);

	@Override
	public ComponentDTO saveComponent(ComponentDTO componentDTO) throws com.onenzero.ozerp.appbase.error.exception.TransformerException {
		Component component = componentTransformer.transformDTOToDomain(componentDTO);
		return componentTransformer.transformDomainToDTO(componentRepository.saveAndFlush(component));
	}

	@Override
	public ComponentDTO getComponentById(Long id) throws NotFoundException, com.onenzero.ozerp.appbase.error.exception.TransformerException {
		if (!componentRepository.existsById(id)) {
			throw new NotFoundException(EntityNotFoundConstant.COMPONENT_NOT_FOUND + id);
		}
		return componentTransformer.transformDomainToDTO(componentRepository.findById(id).get());
	}

	@Override
	public List<ComponentDTO> getAllComponents() throws TransformerException {
		return componentTransformer.transformDomainToDTO(componentRepository.findAll());
	}

}