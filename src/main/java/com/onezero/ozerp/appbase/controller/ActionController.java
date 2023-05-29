package com.onezero.ozerp.appbase.controller;

import com.onezero.ozerp.appbase.dto.ActionDTO;
import com.onezero.ozerp.appbase.dto.PermissionDTO;
import com.onezero.ozerp.appbase.dto.PermissionManageDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseDTO;
import com.onezero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.TransformerException;
import com.onezero.ozerp.appbase.service.ActionService;
import com.onezero.ozerp.appbase.service.PermissionService;
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
import java.util.List;


@RestController
@RequestMapping("api/v1")
@Tag(name = "access-manager", description = "Access Manager API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class ActionController {

    @Autowired
    private ActionService actionService;

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permissions")
    @PreAuthorize("hasAuthority('ADD_PERMISSION')")
    public ResponseDTO<?> savePermission(@Valid @RequestBody PermissionManageDTO permissionManageDTO) throws TransformerException {

        ResponseDTO<List<PermissionDTO>> response = new ResponseDTO<>();
        response.setPayload(permissionService.savePermission(permissionManageDTO));
        return CommonUtils.updateResponse(response);
    }

    @PostMapping("/actions")
    @PreAuthorize("hasAuthority('ADD_ACTION')")
    public ResponseDTO<?> saveAction(@Valid @RequestBody ActionDTO actionDTO) throws TransformerException {

        ResponseDTO<ActionDTO> response = new ResponseDTO<>();
        response.setPayload(actionService.saveAction(actionDTO));
        return CommonUtils.updateResponse(response);
    }

    @GetMapping("/actions/{id}")
    @PreAuthorize("hasAuthority('VIEW_ACTION')")
    public ResponseDTO<?> getActionById(@PathVariable Long id) throws NotFoundException, TransformerException {

        ResponseDTO<ActionDTO> response = new ResponseDTO<>();
        response.setPayload(actionService.getActionById(id));
        return CommonUtils.updateResponse(response);

    }

    @GetMapping("/actions")
    @PreAuthorize("hasAuthority('VIEW_LIST_ACTION')")
    public ResponseListDTO<?> getAllActions(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(required = false) String sort) throws TransformerException {

        ResponseListDTO<ActionDTO> response = actionService.getAllActions(page, size, sort);
        return CommonUtils.updateResponse(response);
    }

}