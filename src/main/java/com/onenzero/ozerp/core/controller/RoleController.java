package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.RoleDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.enums.ResultStatus;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.TransformerException;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/roles")
	@PreAuthorize("hasAuthority('ADD_ROLE')")
	public ResponseDTO<?> saveRole(@Valid @RequestBody RoleDTO roleDTO) throws TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException {

		ResponseDTO<RoleDTO> response = new ResponseDTO<>();
		response.setPayload(roleService.saveRole(roleDTO));
		return updateResponse(response);
	}

	@GetMapping("/roles/{id}")
	@PreAuthorize("hasAuthority('VIEW_ROLE')")
	public ResponseDTO<?> getRoleById(@PathVariable Long id) throws NotFoundException, TransformerException, javax.xml.transform.TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException {

		ResponseDTO<RoleDTO> response = new ResponseDTO<>();
		response.setPayload(roleService.getRoleById(id));
		return updateResponse(response);

	}

	@GetMapping("/roles")
	@PreAuthorize("hasAuthority('VIEW_LIST_ROLE')")
	public ResponseListDTO<?> getAllRoles() throws TransformerException, javax.xml.transform.TransformerException, com.onenzero.ozerp.core.error.exception.TransformerException {

		List<RoleDTO> roleDTOList = roleService.getAllRoles();
		ResponseListDTO<RoleDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(roleDTOList);
		response.setCount(roleDTOList.size());
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

}