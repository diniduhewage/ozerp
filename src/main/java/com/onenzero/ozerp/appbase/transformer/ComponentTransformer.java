package com.onenzero.ozerp.appbase.transformer;

import com.onenzero.ozerp.appbase.dto.ComponentDTO;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.util.SaasUtil;
import org.springframework.stereotype.Component;


@Component
public class ComponentTransformer extends AbstractTransformer<com.onenzero.ozerp.appbase.entity.Component, ComponentDTO> {

	@Override
	public ComponentDTO transformDomainToDTO(com.onenzero.ozerp.appbase.entity.Component domain) throws TransformerException {
		ComponentDTO dto = new ComponentDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		return dto;
	}

	@Override
	public com.onenzero.ozerp.appbase.entity.Component transformDTOToDomain(ComponentDTO dto) throws TransformerException {
		com.onenzero.ozerp.appbase.entity.Component domain = new com.onenzero.ozerp.appbase.entity.Component();
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