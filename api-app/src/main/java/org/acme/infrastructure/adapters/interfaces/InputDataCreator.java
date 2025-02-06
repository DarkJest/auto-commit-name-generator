package org.acme.infrastructure.adapters.interfaces;

import org.acme.domain.entity.EditedFile;

import java.util.List;

public interface InputDataCreator {
    public String createInputData(List<EditedFile> editedFiles);
}
