package com.onenzero.ozerp.core.transformer;

import com.onenzero.ozerp.core.dto.ActionDTO;
import com.onenzero.ozerp.core.entity.Action;
import com.onenzero.ozerp.core.util.CommonUtils;
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
		domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
		domain.setModifiedDate(CommonUtils.timeStampGenerator());
		return domain;
	}

}