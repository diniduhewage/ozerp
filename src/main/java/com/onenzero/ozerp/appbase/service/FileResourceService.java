package com.onenzero.ozerp.appbase.service;


import com.onenzero.ozerp.appbase.dto.FileDetailDto;
import com.onenzero.ozerp.appbase.error.exception.AccessManagerRuntimeException;

public interface FileResourceService {

	FileDetailDto getFiles(String local, String username) throws AccessManagerRuntimeException;
	
	boolean getBusinessId(String local, String username, boolean isClientValidate) throws  AccessManagerRuntimeException;

}
