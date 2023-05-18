package com.onenzero.ozerp.core.service.impl;

import com.onenzero.ozerp.core.constant.MessageConstants;
import com.onenzero.ozerp.core.dto.ComponentDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.entity.Component;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.repository.ComponentRepository;
import com.onenzero.ozerp.core.service.GenericService;
import com.onenzero.ozerp.core.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ComponentService implements GenericService<ComponentDTO, ComponentDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentService.class);
    private final ComponentRepository componentRepository;

    @Override
    public ComponentDTO save(ComponentDTO componentDTO) {
        LOGGER.debug("Saving component: " + componentDTO);
        componentDTO.setCreatedDate(CommonUtils.timeStampGenerator());
        Component component = new Component();
        BeanUtils.copyProperties(componentDTO, component);
        Component savedComponent = componentRepository.save(component);
        ComponentDTO returnComponentDTO = new ComponentDTO();
        BeanUtils.copyProperties(savedComponent, returnComponentDTO);
        return returnComponentDTO;
    }

    @Override
    public ComponentDTO getById(Long id) {
        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id));
        ComponentDTO componentDTO = new ComponentDTO();
        BeanUtils.copyProperties(component, componentDTO);
        return componentDTO;
    }

    @Override
    public ResponseListDTO<ComponentDTO> getAll(Pageable pageable) {
        Page<Component> pageResponse = componentRepository.findAll(pageable);
        List<ComponentDTO> componentDTOList = pageResponse.map(component -> {
            ComponentDTO componentDTO = new ComponentDTO();
            BeanUtils.copyProperties(component, componentDTO);
            return componentDTO;
        }).stream().collect(Collectors.toList());
        return new ResponseListDTO<>(componentDTOList,
                pageResponse.getTotalPages(),
                pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(),
                pageResponse.getNumber(),
                pageResponse.getSort(),
                pageResponse.getNumberOfElements());
    }

    @Override
    public ComponentDTO update(Long id, ComponentDTO componentDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        if (componentRepository.existsById(id)) {
            LOGGER.debug("Deleting component for id: " + id);
            componentRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id);
        }
    }
}
