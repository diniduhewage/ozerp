package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.constant.EntityNotFoundConstant;
import com.onezero.ozerp.dto.CompanyAddressDTO;
import com.onezero.ozerp.dto.CompanyDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.Company;
import com.onezero.ozerp.repository.CompanyAddressRepository;
import com.onezero.ozerp.repository.CompanyRepository;
import com.onezero.ozerp.service.CompanyService;
import com.onezero.ozerp.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAddressRepository companyAddressRepository;

    @Override
    public CompanyDTO getById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND));
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(company, companyDTO);
        return companyDTO;
    }

    @Override
    public CompanyDTO getByCompanyId(String companyId) {
        Company company = companyRepository.findByCompanyId(companyId);
        if (company == null) {
            throw new EntityNotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND);
        }
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(company, companyDTO);
        return companyDTO;
    }

    @Override
    public ResponseListDTO<CompanyDTO> getAll(Pageable pageable) {
        Page<Company> pageResponse =  companyRepository.findAll(pageable);
        List<CompanyDTO> companyDTOList = pageResponse.map(company -> {
            CompanyDTO companyDTO = new CompanyDTO();
            BeanUtils.copyProperties(company, companyDTO);
            return companyDTO;
        }).stream().collect(Collectors.toList());
        return new ResponseListDTO<>(companyDTOList,
                pageResponse.getTotalPages(),
                pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(),
                pageResponse.getNumber(),
                pageResponse.getSort(),
                pageResponse.getNumberOfElements());
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        companyDTO.setCreatedDate(CommonUtils.timeStampGenerator());
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company);
        Company savedCompany = companyRepository.save(company);
        CompanyDTO returnCompanyDTO = new CompanyDTO();
        BeanUtils.copyProperties(savedCompany, returnCompanyDTO);
        return returnCompanyDTO;
    }

    @Override
    public CompanyDTO update(Long id, CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public CompanyAddressDTO saveAddress(CompanyAddressDTO companyAddressDTO) {
        return null;
    }

    @Override
    public CompanyAddressDTO updateAddress(Long id, CompanyAddressDTO companyAddressDTO) {
        return null;
    }
}
