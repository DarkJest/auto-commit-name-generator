package org.acme.infrastructure.adapters.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.entity.EditedFile;
import org.acme.infrastructure.adapters.interfaces.InputDataCreator;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class DefaultInputDataCreator implements InputDataCreator {
    private final String prompt;

    public DefaultInputDataCreator(@ConfigProperty(name = "app.adapters.github.prompt") String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String createInputData(List<EditedFile> editedFiles) {
        StringBuilder promptBuilder = new StringBuilder(prompt);
        for (EditedFile file : editedFiles) {
            promptBuilder.append("\n").append(file.filePath());
        }
        return promptBuilder.toString();
    }
}
