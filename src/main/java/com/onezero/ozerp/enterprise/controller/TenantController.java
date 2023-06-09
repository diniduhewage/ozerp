package com.onezero.ozerp.enterprise.controller;


import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.enums.ResultStatus;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.enterprise.dto.TenantDTO;
import com.onezero.ozerp.enterprise.service.TenantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/v1")
@Tag(name = "Tenant", description = "TenantController")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class TenantController {

    private TenantService tenantService;
    @PostMapping("/tenant")
    @PreAuthorize("hasAuthority('ADD_TENANT')")
    public ResponseDTO<?> saveTenant(@Valid @RequestBody TenantDTO tenantDTO) {
        ResponseDTO<TenantDTO> response = new ResponseDTO<>();
        response.setPayload(tenantService.saveTenant(tenantDTO));
        return updateResponse(response);
    }

    /**
     * Update a tenant by ID.There are some field cannot be updated like tenant_api_key, created by & date, Id etc.
     *
     * @param id         The ID of the tenant to update.
     * @param tenantDTO  The updated tenant data.
     * @return A response containing the updated tenant.
     */
    @PutMapping("/tenant/{id}")
    @PreAuthorize("hasAuthority('EDIT_TENANT')")
    public ResponseDTO<?> updateTenant(@PathVariable Long id,@Valid @RequestBody TenantDTO tenantDTO) {
        ResponseDTO<TenantDTO> response = new ResponseDTO<>();
        response.setPayload(tenantService.updateTenant(tenantDTO,id));
        return updateResponse(response);
    }

    @GetMapping("/tenant/{id}")
    @PreAuthorize("hasAuthority('VIEW_TENANT')")
    public ResponseDTO<?> getTenantById(@PathVariable Long id) throws NotFoundException {
        ResponseDTO<TenantDTO> response = new ResponseDTO<>();
        response.setPayload(tenantService.getTenant(id));
        return updateResponse(response);

    }
    @GetMapping("/tenants")
    @PreAuthorize("hasAuthority('VIEW_LIST_TENANT')")
    public ResponseListDTO<?> getAllTenants() throws DataAccessException { // pagination should be implemented
        List<TenantDTO> tenantDTOList = tenantService.getAllTenants();
        ResponseListDTO<TenantDTO> response = new ResponseListDTO<>();
        response.setPayloadDto(tenantDTOList);
        return updateResponse(response);

    }

    /**
     * Delete a tenant by ID.Here performs a soft delete which is not possible to revert using an api
     *
     * @param id The ID of the tenant to delete.
     * @return A response containing the deleted tenant, contains a flag isDeleted equals to true.
     */
    @DeleteMapping("/tenant/{id}")
    @PreAuthorize("hasAuthority('DELETE_TENANT')")
    public ResponseDTO<?> deleteTenant(@PathVariable Long id) {
        ResponseDTO<TenantDTO> response = new ResponseDTO<>();
        response.setPayload(tenantService.deleteTenant(id));
        return updateResponse(response);
    }
    @Autowired
    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }

    private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
        response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
        return response;
    }
}
