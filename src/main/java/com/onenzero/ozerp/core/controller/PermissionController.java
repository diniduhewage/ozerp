package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.PermissionDTO;
import com.onenzero.ozerp.core.dto.PermissionDeleteDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.enums.ResultStatus;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@PostMapping("/permissions")
	@PreAuthorize("hasAuthority('ADD_PERMISSION')")
	public ResponseDTO<?> savePermission(@Valid @RequestBody PermissionDTO permissionDTO) throws TransformerException {

		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.savePermission(permissionDTO));
        return updateResponse(response);
	}

	@GetMapping("/permissions/{id}")
	@PreAuthorize("hasAuthority('VIEW_PERMISSION')")
	public ResponseDTO<?> getPermissionById(@PathVariable Long id) throws NotFoundException, TransformerException {

		ResponseDTO<PermissionDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.getPermissionById(id));
		return updateResponse(response);

	}

	@GetMapping("/permissions")
	@PreAuthorize("hasAuthority('VIEW_LIST_PERMISSION')")
	public ResponseListDTO<?> getAllPermissions() throws TransformerException {

		List<PermissionDTO> permissionDTOList = permissionService.getAllPermissions();
		ResponseListDTO<PermissionDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(permissionDTOList);
		response.setCount(permissionDTOList.size());
		return updateResponse(response);

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

	@DeleteMapping("/permission")
	@PreAuthorize("hasAuthority('DELETE_ROLE')")
	public ResponseDTO<?> saveMerchant(@RequestBody PermissionDeleteDTO permissionDeleteDTO) throws TransformerException {

		ResponseDTO<PermissionDeleteDTO> response = new ResponseDTO<>();
		response.setPayload(permissionService.deletePermissionByRoleAndPermission(permissionDeleteDTO));
		return updateResponse(response);
	}

}