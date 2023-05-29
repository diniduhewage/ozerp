package com.onezero.ozerp.appbase.transformer;

import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.appbase.dto.ComponentDTO;
import org.springframework.stereotype.Component;


@Component
public class ComponentTransformer extends AbstractTransformer<com.onezero.ozerp.appbase.entity.Component, ComponentDTO> {

    @Override
    public ComponentDTO transformDomainToDTO(com.onezero.ozerp.appbase.entity.Component domain) throws TransformerException {
        ComponentDTO dto = new ComponentDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setCode(domain.getCode());
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        return dto;
    }

    @Override
    public com.onezero.ozerp.appbase.entity.Component transformDTOToDomain(ComponentDTO dto) throws TransformerException {
        com.onezero.ozerp.appbase.entity.Component domain = new com.onezero.ozerp.appbase.entity.Component();
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