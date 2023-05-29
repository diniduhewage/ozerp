package com.onezero.ozerp.enterprise.service.impl;

import com.onezero.ozerp.appbase.constant.EntityNotFoundConstant;
import com.onezero.ozerp.enterprise.dto.CompanyAddressDTO;
import com.onezero.ozerp.enterprise.dto.CompanyDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.enterprise.entity.Company;
import com.onezero.ozerp.enterprise.entity.CompanyAddress;
import com.onezero.ozerp.appservice.entity.IsoCountry;
import com.onezero.ozerp.appservice.entity.IsoCurrency;
import com.onezero.ozerp.appbase.error.exception.BadRequestException;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.repository.CompanyAddressRepository;
import com.onezero.ozerp.enterprise.repository.CompanyRepository;
import com.onezero.ozerp.appbase.repository.IsoCountryRepository;
import com.onezero.ozerp.appbase.repository.IsoCurrencyRepository;
import com.onezero.ozerp.enterprise.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAddressRepository companyAddressRepository;
    private final IsoCurrencyRepository isoCurrencyRepository;
    private final IsoCountryRepository isoCountryRepository;

    @Override
    public CompanyDTO getById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new NotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND + id));
        List<CompanyAddressDTO> addressDTOList = new ArrayList<>();
        company.getAddresses().forEach(companyAddress -> {
            CompanyAddressDTO companyAddressDTO = new CompanyAddressDTO();
            BeanUtils.copyProperties(companyAddress, companyAddressDTO);
            addressDTOList.add(companyAddressDTO);
        });
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(company, companyDTO);
        companyDTO.setAddresses(addressDTOList);
        return companyDTO;
    }

    @Override
    public CompanyDTO getByCompanyId(String companyId) {
        Company company = companyRepository.findByCompanyId(companyId);
        if (company == null) {
            throw new NotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND);
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
        IsoCurrency currency = isoCurrencyRepository.findByCurrencyCode(companyDTO.getAccountingCurrency().getCurrencyCode());
        if (currency == null) {
            throw new NotFoundException(String.format(EntityNotFoundConstant.INVALID_CURRENCY, companyDTO.getAccountingCurrency().getCurrencyCode()));
        }
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company);
        company.setAccountingCurrency(currency);
        Company savedCompany = companyRepository.save(company);
        CompanyDTO returnCompanyDTO = new CompanyDTO();
        BeanUtils.copyProperties(savedCompany, returnCompanyDTO);
        return returnCompanyDTO;
    }

    @Override
    public CompanyDTO update(Long id, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND + companyDTO.getId()));
        if(StringUtils.hasText(companyDTO.getCompanyId()) && !company.getCompanyId().equals(companyDTO.getCompanyId())) {
            company.setDescription(companyDTO.getDescription());
        }
        if(StringUtils.hasText(companyDTO.getDescription())) {
            company.setDescription(companyDTO.getDescription());
        }
        if(companyDTO.getAccountingCurrency() != null && !company.getAccountingCurrency().equals(companyDTO.getAccountingCurrency())) {
            IsoCurrency currency = isoCurrencyRepository.findByCurrencyCode(companyDTO.getAccountingCurrency().getCurrencyCode());
            if (currency == null) {
                throw new NotFoundException(String.format(EntityNotFoundConstant.INVALID_CURRENCY, companyDTO.getAccountingCurrency().getCurrencyCode()));
            }
            company.setAccountingCurrency(currency);
        }
        Company savedCompany = companyRepository.save(company);
        CompanyDTO returnCompanyDTO = new CompanyDTO();
        BeanUtils.copyProperties(savedCompany, returnCompanyDTO);
        return returnCompanyDTO;
    }

    @Override
    public boolean delete(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND + id);
        }
    }

    @Override
    public CompanyAddressDTO saveAddress(CompanyAddressDTO companyAddressDTO) {
        IsoCountry currency = isoCountryRepository.findByCountryCode(companyAddressDTO.getCountry().getCountryCode());
        if (currency == null) {
            throw new NotFoundException(String.format(EntityNotFoundConstant.INVALID_COUNTRY, companyAddressDTO.getCountry().getCountryCode()));
        }
        if(companyAddressDTO.getCompany() == null || companyAddressDTO.getCompany().getId() == null){
            throw new BadRequestException("Company id is required");
        }
        Company company = companyRepository.findById(companyAddressDTO.getCompany().getId())
                .orElseThrow(() -> new NotFoundException(EntityNotFoundConstant.RECORD_NOT_FOUND + companyAddressDTO.getCompany().getId()));
        CompanyAddress companyAddress = new CompanyAddress();
        BeanUtils.copyProperties(companyAddressDTO, companyAddress);
        companyAddress.setCompany(company);
        CompanyAddress savedCompanyAddress = companyAddressRepository.save(companyAddress);
        CompanyAddressDTO returnCompanyAddressDTO = new CompanyAddressDTO();
        BeanUtils.copyProperties(savedCompanyAddress, returnCompanyAddressDTO);
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(savedCompanyAddress.getCompany(), companyDTO);
        returnCompanyAddressDTO.setCompany(companyDTO);
        return returnCompanyAddressDTO;
    }

    @Override
    public CompanyAddressDTO updateAddress(Long id, CompanyAddressDTO companyAddressDTO) {
        return null;
    }
}
