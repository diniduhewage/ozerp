package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.ComponentDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.service.impl.ComponentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

//@Tag(name = "COMPONENT", description = "COMPONENT API")
//@SecurityRequirements({
//        @SecurityRequirement(name = "bearer-jwt")
//})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/component")
public class ComponentController {
    private final ComponentService componentService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_COMPONENT')")
    public ResponseDTO<?> saveComponent(@Valid @RequestBody ComponentDTO componentDTO) {

        ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
        response.setPayload(componentService.save(componentDTO));
        return ResponseDTO.response(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_COMPONENT')")
    public ResponseDTO<?> getComponentById(@PathVariable Long id) throws EntityNotFoundException {

        ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
        response.setPayload(componentService.getById(id));
        return ResponseDTO.response(response);

    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_LIST_COMPONENT')")
    public ResponseListDTO<?> getAllComponent(Pageable pageable) {
        ResponseListDTO<ComponentDTO> response = componentService.getAll(pageable);
        return ResponseListDTO.generateResponse(response);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_COMPONENT')")
    public ResponseDTO<?> updateComponentById(@PathVariable Long id, @RequestBody @Valid ComponentDTO componentDTO) {
        ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
        response.setPayload(componentService.update(id, componentDTO));
        return ResponseDTO.response(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_COMPONENT')")
    public ResponseDTO<?> deleteComponentById(@PathVariable Long id) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        response.setPayload(componentService.delete(id));
        return ResponseDTO.response(response);
    }
}
