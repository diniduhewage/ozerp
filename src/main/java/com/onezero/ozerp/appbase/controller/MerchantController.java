package com.onezero.ozerp.appbase.controller;

import com.onezero.ozerp.appbase.dto.MerchantDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.service.MerchantService;
import com.onezero.ozerp.appbase.util.CommonUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;


@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("api/v1")
@Tag(name = "access-manager", description = "Access Manager API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/merchants")
    @PreAuthorize("hasAuthority('ADD_MERCHANT')")
    public ResponseDTO<?> saveMerchant(@Valid @RequestBody MerchantDTO merchantDTO) throws TransformerException {

        ResponseDTO<MerchantDTO> response = new ResponseDTO<>();
        response.setPayload(merchantService.saveMerchant(merchantDTO));
        return CommonUtils.updateResponse(response);
    }

    @GetMapping("/merchants/{id}")
    @PreAuthorize("hasAuthority('VIEW_MERCHANT')")
    public ResponseDTO<?> getMerchantById(@PathVariable Long id) throws EntityNotFoundException, TransformerException {

        ResponseDTO<MerchantDTO> response = new ResponseDTO<>();
        response.setPayload(merchantService.getMerchantById(id));
        return CommonUtils.updateResponse(response);

    }

    @GetMapping("/merchants")
    @PreAuthorize("hasAuthority('VIEW_LIST_MERCHANT')")
    public ResponseListDTO<?> getAllMerchants(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(required = false) String sort) throws TransformerException {

        ResponseListDTO<MerchantDTO> response = merchantService.getAllMerchants(page, size, sort);
        return CommonUtils.updateResponse(response);

    }

    @PutMapping("/merchants/{id}")
    @PreAuthorize("hasAuthority('EDIT_MERCHANT')")
    public ResponseDTO<?> updateMerchantById(@PathVariable Long id, @RequestBody @Valid MerchantDTO merchantDTO) throws TransformerException {
        ResponseDTO<MerchantDTO> response = new ResponseDTO<>();
        response.setPayload(merchantService.updateMerchantById(id, merchantDTO));
        return CommonUtils.updateResponse(response);
    }

    @DeleteMapping("/merchants/{id}")
    @PreAuthorize("hasAuthority('DELETE_MERCHANT')")
    public ResponseDTO<?> deletedMerchant(@PathVariable Long id) throws TransformerException {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        response.setPayload(merchantService.deleteMerchantById(id));
        return CommonUtils.updateResponse(response);
    }

}