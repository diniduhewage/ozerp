package com.onezero.ozerp.enterprise.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.enterprise.dto.UserTenantDTO;
import com.onezero.ozerp.enterprise.service.UserTenantService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/v1")
@Tag(name = "access-manager", description = "Access Manager API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class UserTenantController {

    @Autowired
    private UserTenantService userTenantService;

   

    @PostMapping("/user-tenants")
    @PreAuthorize("hasAuthority('ADD_USER_TENANT')")
    public ResponseDTO<?> saveUserTenant(@Valid @RequestBody UserTenantDTO userTenantDTO) throws TransformerException {

        ResponseDTO<UserTenantDTO> response = new ResponseDTO<>();
        response.setPayload(userTenantService.save(userTenantDTO));
        return CommonUtils.updateResponse(response);
    }

    
}