package com.onenzero.ozerp.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDetailDto {

    private String businessId;
    private String manifestCreated;
    private String softwareVersion;
    private List<FileResourceDto> files = new ArrayList<>();

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getManifestCreated() {
        return manifestCreated;
    }

    public void setManifestCreated(String manifestCreated) {
        this.manifestCreated = manifestCreated;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public List<FileResourceDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileResourceDto> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FileDetailDto [businessId=");
        builder.append(businessId);
        builder.append(", manifestCreated=");
        builder.append(manifestCreated);
        builder.append(", softwareVersion=");
        builder.append(softwareVersion);
        builder.append(", files=");
        builder.append(files);
        builder.append("]");
        return builder.toString();
    }


}
