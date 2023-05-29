package com.onezero.ozerp.appbase.controller;

import com.onezero.ozerp.appbase.dto.PermissionDTO;
import com.onezero.ozerp.appbase.dto.PermissionDeleteDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.service.PermissionService;
import com.onezero.ozerp.appbase.util.CommonUtils;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1")
@Tag(name = "access-manager", description = "Access Manager API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

//	@PostMapping("/permissions")
//	@PreAuthorize("hasAuthority('ADD_PERMISSION')")
//	public ResponseDTO<?> savePermission(@Valid @RequestBody PermissionDTO permissionDTO) throws TransformerException {
//		
//		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
//		response.setPayload(permissionService.savePermission(permissionDTO));
//        return updateResponse(response);
//	}

    @GetMapping("/permissions/{id}")
    @PreAuthorize("hasAuthority('VIEW_PERMISSION')")
    public ResponseDTO<?> getPermissionById(@PathVariable Long id) throws NotFoundException, TransformerException {

        ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
        response.setPayload(permissionService.getPermissionById(id));
        return CommonUtils.updateResponse(response);

    }

    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('VIEW_LIST_PERMISSION')")
    public ResponseListDTO<?> getAllPermissions(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                @RequestParam(required = false) String sort) throws TransformerException {

        ResponseListDTO<PermissionDTO> response = permissionService.getAllPermissions(page, size, sort);
        return CommonUtils.updateResponse(response);

    }

    @DeleteMapping("/permission")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public ResponseDTO<?> saveMerchant(@RequestBody PermissionDeleteDTO permissionDeleteDTO) throws TransformerException {

        ResponseDTO<PermissionDeleteDTO> response = new ResponseDTO<>();
        response.setPayload(permissionService.deletePermissionByRoleAndPermission(permissionDeleteDTO));
        return CommonUtils.updateResponse(response);
    }

}