package org.acme.presentation.http.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EditedFilesRequest implements IRequest {
    @JsonProperty("editedFiles")
    private List<String> editedFiles;

    // Пустой конструктор для JSON-десериализации
    public EditedFilesRequest() {}

    // Конструктор с параметром для удобства создания объекта
    @JsonCreator
    public EditedFilesRequest(@JsonProperty("editedFiles") List<String> editedFiles) {
        this.editedFiles = editedFiles;
    }

    // Геттер для поля editedFiles
    public List<String> getEditedFiles() {
        return editedFiles;
    }

    // Сеттер для поля editedFiles
    public void setEditedFiles(List<String> editedFiles) {
        this.editedFiles = editedFiles;
    }
}
