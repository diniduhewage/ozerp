package com.onezero.ozerp.appbase.transformer;

import com.onezero.ozerp.appbase.entity.Action;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.appbase.dto.ActionDTO;
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
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
        domain.setModifiedDate(CommonUtils.timeStampGenerator());
        return domain;
    }

}