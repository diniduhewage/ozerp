package com.onenzero.ozerp.core.controller;

import com.onenzero.ozerp.core.dto.ComponentDTO;
import com.onenzero.ozerp.core.dto.response.ResponseDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.enums.ResultStatus;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.error.exception.TransformerException;
import com.onenzero.ozerp.core.service.ComponentService;
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

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ComponentController {

	@Autowired
	private ComponentService componentService;

	@PostMapping("/components")
	@PreAuthorize("hasAuthority('ADD_COMPONENT')")
	public ResponseDTO<?> saveComponent(@Valid @RequestBody ComponentDTO componentDTO) throws TransformerException {

		ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
		response.setPayload(componentService.saveComponent(componentDTO));
        return updateResponse(response);
	}

	@GetMapping("/components/{id}")
	@PreAuthorize("hasAuthority('VIEW_COMPONENT')")
	public ResponseDTO<?> getComponentById(@PathVariable Long id) throws NotFoundException, TransformerException {

		ResponseDTO<ComponentDTO> response = new ResponseDTO<>();
		response.setPayload(componentService.getComponentById(id));
		return updateResponse(response);

	}

	@GetMapping("/components")
	@PreAuthorize("hasAuthority('VIEW_LIST_COMPONENT')")
	public ResponseListDTO<?> getAllComponents() throws TransformerException {

		List<ComponentDTO> componentDTOList = componentService.getAllComponents();
		ResponseListDTO<ComponentDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(componentDTOList);
		response.setCount(componentDTOList.size());
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