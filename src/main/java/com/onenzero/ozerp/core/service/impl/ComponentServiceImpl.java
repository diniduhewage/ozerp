package com.onenzero.ozerp.core.service.impl;

import com.onenzero.ozerp.core.constant.MessageConstants;
import com.onenzero.ozerp.core.dto.ComponentDTO;
import com.onenzero.ozerp.core.entity.Component;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.repository.ComponentRepository;
import com.onenzero.ozerp.core.service.ComponentService;
import com.onenzero.ozerp.core.transformer.ComponentTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComponentServiceImpl implements ComponentService {

    private final Logger logger = LoggerFactory.getLogger(ComponentService.class);
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentTransformer componentTransformer;

    @Override
    public ComponentDTO saveComponent(ComponentDTO componentDTO) throws com.onenzero.ozerp.core.error.exception.TransformerException {
        Component component = componentTransformer.transformDTOToDomain(componentDTO);
        return componentTransformer.transformDomainToDTO(componentRepository.saveAndFlush(component));
    }

    @Override
    public ComponentDTO getComponentById(Long id) throws NotFoundException, com.onenzero.ozerp.core.error.exception.TransformerException {
        if (!componentRepository.existsById(id)) {
            throw new NotFoundException(MessageConstants.COMPONENT_NOT_FOUND + id);
        }
        return componentTransformer.transformDomainToDTO(componentRepository.findById(id).get());
    }

    @Override
    public List<ComponentDTO> getAllComponents() throws TransformerException {
        return componentTransformer.transformDomainToDTO(componentRepository.findAll());
    }

}