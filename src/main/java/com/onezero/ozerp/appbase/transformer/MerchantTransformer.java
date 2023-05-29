package com.onezero.ozerp.appbase.transformer;

import com.onezero.ozerp.appbase.entity.Merchant;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.appbase.dto.MerchantDTO;
import org.springframework.stereotype.Component;


@Component
public class MerchantTransformer extends AbstractTransformer<Merchant, MerchantDTO> {


    @Override
    public MerchantDTO transformDomainToDTO(Merchant domain) throws TransformerException {
        MerchantDTO dto = new MerchantDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setCode(domain.getCode());
        dto.setCreatedDate(domain.getCreatedDate());
        dto.setModifiedDate(domain.getModifiedDate());
        return dto;
    }

    @Override
    public Merchant transformDTOToDomain(MerchantDTO dto) throws TransformerException {
        Merchant domain = new Merchant();
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