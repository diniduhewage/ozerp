package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.ActionDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.service.ActionService;
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

//@Tag(name = "ACTION", description = "ACTION API")
//@SecurityRequirements({
//        @SecurityRequirement(name = "bearer-jwt")
//})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/action")
public class ActionController {
    private final ActionService actionService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_ACTION')")
    public ResponseDTO<?> saveAction(@Valid @RequestBody ActionDTO actionDTO) {

        ResponseDTO<ActionDTO> response = new ResponseDTO<>();
        response.setPayload(actionService.save(actionDTO));
        return ResponseDTO.response(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_ACTION')")
    public ResponseDTO<?> getActionById(@PathVariable Long id) throws EntityNotFoundException {

        ResponseDTO<ActionDTO> response = new ResponseDTO<>();
        response.setPayload(actionService.getById(id));
        return ResponseDTO.response(response);

    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_LIST_ACTION')")
    public ResponseListDTO<?> getAllAction(Pageable pageable) {
        ResponseListDTO<ActionDTO> response = actionService.getAll(pageable);
        return ResponseListDTO.generateResponse(response);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_ACTION')")
    public ResponseDTO<?> updateActionById(@PathVariable Long id, @RequestBody @Valid ActionDTO actionDTO) {
        ResponseDTO<ActionDTO> response = new ResponseDTO<>();
        response.setPayload(actionService.update(id, actionDTO));
        return ResponseDTO.response(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ACTION')")
    public ResponseDTO<?> deleteActionById(@PathVariable Long id) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        response.setPayload(actionService.delete(id));
        return ResponseDTO.response(response);
    }
}
