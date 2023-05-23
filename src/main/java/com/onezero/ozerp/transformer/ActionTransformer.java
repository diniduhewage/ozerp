package com.onezero.ozerp.transformer;

import com.onezero.ozerp.dto.ActionDTO;
import com.onezero.ozerp.entity.Action;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.util.SaasUtil;
import org.springframework.stereotype.Component;


@Component
public class ActionTransformer extends AbstractTransformer<Action, ActionDTO> {

    @Override
    public ActionDTO transformDomainToDTO(Action domain) throws TransformerException {
        ActionDTO dto = new ActionDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setCode(domain.getCode());
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        return dto;
    }

    @Override
    public Action transformDTOToDomain(ActionDTO dto) throws TransformerException {
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