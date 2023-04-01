package com.onenzero.ozerp.appbase.controller;

import com.onenzero.ozerp.appbase.dto.ActionDTO;
import com.onenzero.ozerp.appbase.dto.response.ResponseDTO;
import com.onenzero.ozerp.appbase.dto.response.ResponseListDTO;
import com.onenzero.ozerp.appbase.enums.ResultStatus;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import com.onenzero.ozerp.appbase.error.exception.TransformerException;
import com.onenzero.ozerp.appbase.service.ActionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1")
public class ActionController {
	
	@Autowired
	private ActionService actionService;
	
	@PostMapping("/actions")
	@PreAuthorize("hasAuthority('ADD_ACTION')")
	public ResponseDTO<?> saveAction(@Valid @RequestBody ActionDTO actionDTO) throws TransformerException {
		
		ResponseDTO<ActionDTO> response = new ResponseDTO<>();
		response.setPayload(actionService.saveAction(actionDTO));
        return updateResponse(response);
	}

	@GetMapping("/actions/{id}")
	@PreAuthorize("hasAuthority('VIEW_ACTION')")
	public ResponseDTO<?> getActionById(@PathVariable Long id) throws NotFoundException, TransformerException {

		ResponseDTO<ActionDTO> response = new ResponseDTO<>();
		response.setPayload(actionService.getActionById(id));
		return updateResponse(response);
		
	}
	
	@GetMapping("/actions")
	@PreAuthorize("hasAuthority('VIEW_LIST_ACTION')")
	public ResponseListDTO<?> getAllActions() throws TransformerException {
		
		List<ActionDTO> actionDTOList = actionService.getAllActions();
		ResponseListDTO<ActionDTO> response = new ResponseListDTO<>();
		response.setPayloadDto(actionDTOList);
		response.setCount(actionDTOList.size());
		return updateResponse(response);

	}
	
	private ResponseListDTO<?> updateResponse(ResponseListDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}

	private ResponseDTO<?> updateResponse(ResponseDTO<?> response) {
		response.setResultStatus(ResultStatus.SUCCESSFUL);
        response.setHttpStatus(HttpStatus.OK);
        response.setHttpCode(response.getHttpStatus().toString());
		return response;
	}
	
}