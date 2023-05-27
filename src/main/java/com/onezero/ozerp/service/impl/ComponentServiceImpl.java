package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.constant.EntityNotFoundConstant;
import com.onezero.ozerp.dto.ComponentDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.Component;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.ComponentRepository;
import com.onezero.ozerp.service.ComponentService;
import com.onezero.ozerp.transformer.ComponentTransformer;
import com.onezero.ozerp.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ComponentDTO saveComponent(ComponentDTO componentDTO) throws TransformerException {
        Component component = componentTransformer.transformDTOToDomain(componentDTO);
        return componentTransformer.transformDomainToDTO(componentRepository.saveAndFlush(component));
    }

    @Override
    public ComponentDTO getComponentById(Long id) throws NotFoundException, TransformerException {
        if (!componentRepository.existsById(id)) {
            throw new NotFoundException(EntityNotFoundConstant.COMPONENT_NOT_FOUND + id);
        }
        return componentTransformer.transformDomainToDTO(componentRepository.findById(id).get());
    }

    @Override
    public ResponseListDTO<ComponentDTO> getAllComponents(Integer page, Integer size, String sort) throws TransformerException {
        Page<Component> pageResponse = componentRepository.findAll(CommonUtils.createPageRequest(page, size, sort));
        List<ComponentDTO> componentDTOList = componentTransformer.transformDomainToDTO(pageResponse.getContent());
        return new ResponseListDTO<>(componentDTOList, pageResponse.getTotalPages(), pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(), pageResponse.getNumber(),
                pageResponse.getSort(), pageResponse.getNumberOfElements());
    }

}