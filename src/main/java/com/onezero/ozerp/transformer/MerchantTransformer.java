package com.onezero.ozerp.transformer;

import com.onezero.ozerp.dto.MerchantDTO;
import com.onezero.ozerp.entity.Merchant;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.util.SaasUtil;
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
        domain.setCreatedDate(null != dto.getCreatedDate() ? dto.getCreatedDate() : SaasUtil.timeStampGenerator());
        domain.setModifiedDate(SaasUtil.timeStampGenerator());
        return domain;
    }

}