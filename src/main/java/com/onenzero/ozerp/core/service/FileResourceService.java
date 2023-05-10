package com.onenzero.ozerp.core.service;


import com.onenzero.ozerp.core.dto.FileDetailDto;
import com.onenzero.ozerp.core.error.exception.AccessManagerRuntimeException;

public interface FileResourceService {

    FileDetailDto getFiles(String local, String username) throws AccessManagerRuntimeException;

    boolean getBusinessId(String local, String username, boolean isClientValidate) throws  AccessManagerRuntimeException;

}
