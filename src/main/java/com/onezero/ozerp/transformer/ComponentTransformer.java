package com.onezero.ozerp.transformer;

import com.onezero.ozerp.dto.ComponentDTO;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.util.SaasUtil;
import org.springframework.stereotype.Component;


@Component
public class ComponentTransformer extends AbstractTransformer<com.onezero.ozerp.entity.Component, ComponentDTO> {

    @Override
    public ComponentDTO transformDomainToDTO(com.onezero.ozerp.entity.Component domain) throws TransformerException {
        ComponentDTO dto = new ComponentDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setCode(domain.getCode());
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        return dto;
    }

    @Override
    public com.onezero.ozerp.entity.Component transformDTOToDomain(ComponentDTO dto) throws TransformerException {
        com.onezero.ozerp.entity.Component domain = new com.onezero.ozerp.entity.Component();
        if (null != dto.getId()) {
            domain.setId(dto.getId());
        }
        domain.setName(dto.getName());
        domain.setCode(dto.getCode());
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
        domain.setModifiedDate(SaasUtil.timeStampGenerator());
        return domain;
    }

}