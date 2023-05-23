package com.onezero.ozerp.service;


import com.onezero.ozerp.dto.MerchantDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.error.exception.TransformerException;

import javax.persistence.EntityNotFoundException;


public interface MerchantService {

    MerchantDTO saveMerchant(MerchantDTO merchantDTO) throws TransformerException;

    MerchantDTO getMerchantById(Long id) throws EntityNotFoundException, TransformerException;

    ResponseListDTO<MerchantDTO> getAllMerchants(Integer page, Integer size, String sort) throws TransformerException;

    MerchantDTO updateMerchantById(Long id, MerchantDTO departmerchantDTOment) throws TransformerException;

    boolean deleteMerchantById(Long id) throws TransformerException;

}