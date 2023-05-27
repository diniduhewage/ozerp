package com.onezero.ozerp.controller;

import com.onezero.ozerp.dto.RoleDTO;
import com.onezero.ozerp.dto.response.ResponseDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.service.RoleService;
import com.onezero.ozerp.util.CommonUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1")
@Tag(name = "access-manager", description = "Access Manager API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ADD_ROLE')")
    public ResponseDTO<?> saveRole(@Valid @RequestBody RoleDTO roleDTO) throws TransformerException {

        ResponseDTO<RoleDTO> response = new ResponseDTO<>();
        response.setPayload(roleService.saveRole(roleDTO));
        return CommonUtils.updateResponse(response);
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    public ResponseDTO<?> getRoleById(@PathVariable Long id) throws NotFoundException, TransformerException {

        ResponseDTO<RoleDTO> response = new ResponseDTO<>();
        response.setPayload(roleService.getRoleById(id));
        return CommonUtils.updateResponse(response);

    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('VIEW_LIST_ROLE')")
    public ResponseListDTO<?> getAllRoles(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(required = false) String sort) throws TransformerException {

        ResponseListDTO<RoleDTO> response = roleService.getAllRoles(page, size, sort);
        return CommonUtils.updateResponse(response);

    }

}