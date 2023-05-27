package com.onezero.ozerp.service;

import com.onezero.ozerp.dto.CompanyAddressDTO;
import com.onezero.ozerp.dto.CompanyDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyDTO getById(Long id);
    CompanyDTO getByCompanyId(String companyId);
    ResponseListDTO<CompanyDTO> getAll(Pageable pageable);
    CompanyDTO save(CompanyDTO companyDTO);
    CompanyDTO update(Long id, CompanyDTO companyDTO);
    boolean delete(Long id);

    CompanyAddressDTO saveAddress(CompanyAddressDTO companyAddressDTO);
    CompanyAddressDTO updateAddress(Long id, CompanyAddressDTO companyAddressDTO);
}
