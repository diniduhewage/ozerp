package com.onezero.ozerp.appbase.controller;


import com.onezero.ozerp.appbase.dto.ApiResponseDto;
import com.onezero.ozerp.appbase.config.LocalelizationConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Locale;


@RestController
@RequestMapping("api/v1/localize")
@Tag(name = "Locale", description = "LocalelizationController")
@Tag(name = "access-manager", description = "Access Manager API")
@SecurityRequirements({
        @SecurityRequirement(name = "bearer-jwt")
})
public class LocalelizationController {

    @Autowired
    private LocalelizationConfig localelizationConfig;

    @Operation(summary = "Create program", description = "Program can be created", tags = {"programDTO"})
    @ApiResponses({
//    	@ApiResponse(responseCode = "201", description = "Created", 
//    			content = { @Content(mediaType = "application/json", schema = @Schema(
//    					implementation = ApiResponseDto.class)), @Content(mediaType = "application/xml", 
//    					schema = @Schema(implementation = ApiResponseDto.class)) }),
            @ApiResponse(responseCode = "200", description = "Success", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseDto> getSource(@RequestHeader("Accept-Language") String locale) {


        String how = localelizationConfig.messageSource().getMessage("how.text", null, new Locale(locale));
        String helloworld = localelizationConfig.messageSource().getMessage("helloworld.text", null,
                new Locale(locale));
        String successMessageResponse = localelizationConfig.messageSource()
                .getMessage("successResponseMessage.text", null, new Locale(locale));

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(how);
        arrayList.add(helloworld);

        ApiResponseDto messageDTO = new ApiResponseDto();
        messageDTO.setCode("00");
        messageDTO.setMessage(successMessageResponse);
        messageDTO.setPayload(arrayList);
        return ResponseEntity.ok(messageDTO);

    }
}
