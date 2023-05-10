package com.onenzero.ozerp.core.transformer;

import com.onenzero.ozerp.core.dto.ComponentDTO;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.util.CommonUtils;
import org.springframework.stereotype.Component;


@Component
public class ComponentTransformer extends AbstractTransformer<com.onenzero.ozerp.core.entity.Component, ComponentDTO> {

    @Override
    public ComponentDTO transformDomainToDTO(com.onenzero.ozerp.core.entity.Component domain) throws TransformerException {
        ComponentDTO dto = new ComponentDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setCode(domain.getCode());
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        return dto;
    }

    @Override
    public com.onenzero.ozerp.core.entity.Component transformDTOToDomain(ComponentDTO dto) throws TransformerException {
        com.onenzero.ozerp.core.entity.Component domain = new com.onenzero.ozerp.core.entity.Component();
        if (null != dto.getId()) {
            domain.setId(dto.getId());
        }
        domain.setName(dto.getName());
        domain.setCode(dto.getCode());
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
        domain.setModifiedDate(CommonUtils.timeStampGenerator());
        return domain;
    }

}