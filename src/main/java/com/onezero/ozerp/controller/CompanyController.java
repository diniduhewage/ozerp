package com.onezero.ozerp.controller;

import com.onezero.ozerp.dto.CompanyAddressDTO;
import com.onezero.ozerp.dto.CompanyDTO;
import com.onezero.ozerp.dto.response.ResponseDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.service.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;


@RestController
@RequestMapping("api/v1")
@Tag(name = "company", description = "Company API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_COMPANY')")
    public ResponseDTO<?> saveCompany(@Valid @RequestBody CompanyDTO companyDTO) {

        ResponseDTO<CompanyDTO> response = new ResponseDTO<>();
        response.setPayload(companyService.save(companyDTO));
        return ResponseDTO.response(response, HttpStatus.CREATED);
    }

    @PostMapping("/address")
    @PreAuthorize("hasAuthority('ADD_COMPANY_ADDRESS')")
    public ResponseDTO<?> saveCompanyAddress(@Valid @RequestBody CompanyAddressDTO companyAddressDTO) {

        ResponseDTO<CompanyAddressDTO> response = new ResponseDTO<>();
        response.setPayload(companyService.saveAddress(companyAddressDTO));
        return ResponseDTO.response(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_COMPANY')")
    public ResponseDTO<?> getCompanyById(@PathVariable Long id) throws EntityNotFoundException {

        ResponseDTO<CompanyDTO> response = new ResponseDTO<>();
        response.setPayload(companyService.getById(id));
        return ResponseDTO.response(response);

    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_LIST_COMPANY')")
    public ResponseListDTO<?> getAllCompany(Pageable pageable) {
        ResponseListDTO<CompanyDTO> response = companyService.getAll(pageable);
        return ResponseListDTO.generateResponse(response);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_COMPANY')")
    public ResponseDTO<?> updateCompanyById(@PathVariable Long id, @RequestBody @Valid CompanyDTO companyDTO) {
        ResponseDTO<CompanyDTO> response = new ResponseDTO<>();
        response.setPayload(companyService.update(id, companyDTO));
        return ResponseDTO.response(response);
    }

    @PutMapping("/address/{id}")
    @PreAuthorize("hasAuthority('EDIT_COMPANY_ADDRESS')")
    public ResponseDTO<?> updateCompanyAddressById(@PathVariable Long id, @RequestBody @Valid CompanyAddressDTO companyAddressDTO) {
        ResponseDTO<CompanyAddressDTO> response = new ResponseDTO<>();
        response.setPayload(companyService.updateAddress(id, companyAddressDTO));
        return ResponseDTO.response(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_COMPANY')")
    public ResponseDTO<?> deleteCompanyById(@PathVariable Long id) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        response.setPayload(companyService.delete(id));
        return ResponseDTO.response(response);
    }

}