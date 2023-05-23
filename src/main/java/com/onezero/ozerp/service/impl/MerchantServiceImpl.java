package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.constant.EntityNotFoundConstatnt;
import com.onezero.ozerp.dto.MerchantDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.Merchant;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.MerchantRepository;
import com.onezero.ozerp.service.MerchantService;
import com.onezero.ozerp.transformer.MerchantTransformer;
import com.onezero.ozerp.util.SaasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;


@Service
public class MerchantServiceImpl implements MerchantService {

    private final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    MerchantTransformer merchantTransformer;

    @Override
    public MerchantDTO saveMerchant(MerchantDTO merchantDTO) throws TransformerException {
        Merchant merchant = merchantTransformer.transformDTOToDomain(merchantDTO);
        return merchantTransformer.transformDomainToDTO(merchantRepository.saveAndFlush(merchant));
    }

    @Override
    public MerchantDTO getMerchantById(Long id) throws EntityNotFoundException, TransformerException {
        if (!merchantRepository.existsById(id)) {
            throw new NotFoundException(EntityNotFoundConstatnt.MERCHANT_NOT_FOUND + id);
        }
        return merchantTransformer.transformDomainToDTO(merchantRepository.findById(id).get());
    }

    @Override
    public ResponseListDTO<MerchantDTO> getAllMerchants(Integer page, Integer size, String sort) throws TransformerException {
        Page<Merchant> pageResponse = merchantRepository.findAll(SaasUtil.createPageRequest(page, size, sort));
        List<MerchantDTO> merchantDTOList = merchantTransformer.transformDomainToDTO(pageResponse.getContent());

        return new ResponseListDTO<>(merchantDTOList, pageResponse.getTotalPages(), pageResponse.getTotalElements(),
                pageResponse.isLast(), pageResponse.getSize(), pageResponse.getNumber(), pageResponse.getSort(),
                pageResponse.getNumberOfElements());
    }

    @Override
    public MerchantDTO updateMerchantById(Long id, MerchantDTO merchantDTO) throws TransformerException {

        if (!merchantRepository.existsById(id)) {
            throw new NotFoundException(EntityNotFoundConstatnt.MERCHANT_NOT_FOUND + id);
        } else {
            Merchant savedMerchant = merchantRepository.findById(id).get();
            if (Objects.nonNull(merchantDTO.getName()) && !"".equalsIgnoreCase(merchantDTO.getName())) {
                savedMerchant.setName(merchantDTO.getName());
            }
            if (Objects.nonNull(merchantDTO.getCode()) && !"".equalsIgnoreCase(merchantDTO.getCode())) {
                savedMerchant.setCode(merchantDTO.getCode());
            }
            savedMerchant.setModifiedDate(SaasUtil.timeStampGenerator());
            return merchantTransformer.transformDomainToDTO(merchantRepository.saveAndFlush(savedMerchant));

        }

    }

    @Override
    public boolean deleteMerchantById(Long id) {
        merchantRepository.deleteById(id);
        return true;
    }


}