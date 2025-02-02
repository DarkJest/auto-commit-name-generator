package org.acme.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditedFilesDTO {
    @JsonProperty("message")
    private String message;

    public EditedFilesDTO (String message)
    {
        this.message = message;
    }

}
