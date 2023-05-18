package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.PermissionDTO;
import com.onenzero.ozerp.core.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.service.impl.PermissionService;
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

//@Tag(name = "PERMISSION", description = "PERMISSION API")
//@SecurityRequirements({
//        @SecurityRequirement(name = "bearer-jwt")
//})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/permission")
public class PermissionController {
	private final PermissionService permissionService;

	@PostMapping
	@PreAuthorize("hasAuthority('ADD_PERMISSION')")
	public ResponseDTO<?> savePermission(@Valid @RequestBody PermissionDTO permissionDTO) {

		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.save(permissionDTO));
		return ResponseDTO.response(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('VIEW_PERMISSION')")
	public ResponseDTO<?> getPermissionById(@PathVariable Long id) throws EntityNotFoundException {

		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.getById(id));
		return ResponseDTO.response(response);

	}

	@GetMapping
	@PreAuthorize("hasAuthority('VIEW_LIST_PERMISSION')")
	public ResponseListDTO<?> getAllPermission(Pageable pageable) {
		ResponseListDTO<PermissionDTO> response = permissionService.getAll(pageable);
		return ResponseListDTO.generateResponse(response);

	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('EDIT_PERMISSION')")
	public ResponseDTO<?> updatePermissionById(@PathVariable Long id, @RequestBody @Valid PermissionDTO permissionDTO) {
		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.update(id, permissionDTO));
		return ResponseDTO.response(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('DELETE_PERMISSION')")
	public ResponseDTO<?> deletePermissionById(@PathVariable Long id) {
		ResponseDTO<Boolean> response = new ResponseDTO<>();
		response.setPayload(permissionService.delete(id));
		return ResponseDTO.response(response);
	}

	@DeleteMapping("/permission")
	@PreAuthorize("hasAuthority('DELETE_ROLE')")
	public ResponseDTO<?> deleteByRole(@RequestBody PermissionDeleteDTO permissionDeleteDTO) {
		ResponseDTO<PermissionDeleteDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.deleteByRoleAndPermission(permissionDeleteDTO));
		return ResponseDTO.response(response);
	}
}

