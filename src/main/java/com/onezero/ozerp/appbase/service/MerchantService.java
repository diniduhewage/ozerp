package com.onezero.ozerp.appbase.service;


import com.onezero.ozerp.appbase.dto.MerchantDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.TransformerException;

import javax.persistence.EntityNotFoundException;


public interface MerchantService {

    MerchantDTO saveMerchant(MerchantDTO merchantDTO) throws TransformerException;

    MerchantDTO getMerchantById(Long id) throws EntityNotFoundException, TransformerException;

    ResponseListDTO<MerchantDTO> getAllMerchants(Integer page, Integer size, String sort) throws TransformerException;

    MerchantDTO updateMerchantById(Long id, MerchantDTO departmerchantDTOment) throws TransformerException;

    boolean deleteMerchantById(Long id) throws TransformerException;

}