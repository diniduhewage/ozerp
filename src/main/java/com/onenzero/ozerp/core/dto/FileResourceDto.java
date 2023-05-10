package com.onenzero.ozerp.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResourceDto {
    private String lang;
    private String filename;
    private String description;
    private int size;
    private String created;
    private String contentType;
    private String sha256;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FileResourceDto [lang=");
        builder.append(lang);
        builder.append(", filename=");
        builder.append(filename);
        builder.append(", description=");
        builder.append(description);
        builder.append(", size=");
        builder.append(size);
        builder.append(", created=");
        builder.append(created);
        builder.append(", contentType=");
        builder.append(contentType);
        builder.append(", sha256=");
        builder.append(sha256);
        builder.append("]");
        return builder.toString();
    }


}
