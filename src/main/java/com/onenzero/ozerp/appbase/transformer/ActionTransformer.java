package com.onenzero.ozerp.appbase.transformer;

import com.onenzero.ozerp.appbase.dto.ActionDTO;
import com.onenzero.ozerp.appbase.entity.Action;
import com.onenzero.ozerp.appbase.util.SaasUtil;
import org.springframework.stereotype.Component;


@Component
public class ActionTransformer extends AbstractTransformer<Action, ActionDTO> {

	@Override
	public ActionDTO transformDomainToDTO(Action domain) {
		ActionDTO dto = new ActionDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setModifiedDate(domain.getModifiedDate());
		return dto;
	}

	@Override
	public Action transformDTOToDomain(ActionDTO dto) {
		Action domain = new Action();
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