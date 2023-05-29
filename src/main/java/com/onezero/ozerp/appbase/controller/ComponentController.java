package com.onezero.ozerp.appbase.controller;

import com.onezero.ozerp.appbase.dto.ComponentDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.service.ComponentService;
import com.onezero.ozerp.appbase.util.CommonUtils;
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
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @PostMapping("/components")
    @PreAuthorize("hasAuthority('ADD_COMPONENT')")
    public ResponseDTO<?> saveComponent(@Valid @RequestBody ComponentDTO componentDTO) throws TransformerException {

        ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
        response.setPayload(componentService.saveComponent(componentDTO));
        return CommonUtils.updateResponse(response);
    }

    @GetMapping("/components/{id}")
    @PreAuthorize("hasAuthority('VIEW_COMPONENT')")
    public ResponseDTO<?> getComponentById(@PathVariable Long id) throws NotFoundException, TransformerException {

        ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
        response.setPayload(componentService.getComponentById(id));
        return CommonUtils.updateResponse(response);

    }

    @GetMapping("/components")
    @PreAuthorize("hasAuthority('VIEW_LIST_COMPONENT')")
    public ResponseListDTO<?> getAllComponents(@RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(required = false) String sort) throws TransformerException {

        ResponseListDTO<ComponentDTO> response = componentService.getAllComponents(page, size, sort);
        return CommonUtils.updateResponse(response);

    }

}