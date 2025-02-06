package org.acme.domain.aggregate;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.entity.EditedFile;
import org.acme.domain.service.AIModelService;

import java.util.List;

@ApplicationScoped
public class CommitTitleGenerator {
    private final AIModelService aiModelService;

    public CommitTitleGenerator(AIModelService aiModelService) {
        this.aiModelService = aiModelService;
    }

    public String generate(List<EditedFile> editedFiles) {
        return aiModelService.generateTitle(editedFiles);
    }
}
