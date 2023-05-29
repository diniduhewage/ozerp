package com.onezero.ozerp.appbase.transformer;

import com.onezero.ozerp.appbase.entity.Action;
import com.onezero.ozerp.appbase.entity.Permission;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.appbase.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PermissionTransformer extends AbstractTransformer<Permission, PermissionDTO> {

    @Autowired
    private ActionTransformer actionTransformer;

    @Autowired
    private ComponentTransformer componentTransformer;

    @Override
    public PermissionDTO transformDomainToDTO(Permission domain) throws TransformerException {
        PermissionDTO dto = new PermissionDTO();
        dto.setId(domain.getId());
        dto.setCode(domain.getCode());
        dto.setActionDTO(actionTransformer.transformDomainToDTO(domain.getAction()));
        dto.setComponentDTO(componentTransformer.transformDomainToDTO(domain.getComponent()));
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        return dto;
    }

    @Override
    public Permission transformDTOToDomain(PermissionDTO dto) throws TransformerException {
        Permission domain = new Permission();
        if (null != dto.getId()) {
            domain.setId(dto.getId());
        }

        Action action = actionTransformer.transformDTOToDomain(dto.getActionDTO());
        com.onezero.ozerp.appbase.entity.Component component = componentTransformer.transformDTOToDomain(dto.getComponentDTO());
        domain.setCode(action.getCode() + "_" + component.getCode());
        domain.setAction(action);
        domain.setComponent(component);
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : CommonUtils.timeStampGenerator());
        domain.setModifiedDate(CommonUtils.timeStampGenerator());
        return domain;
    }

}
