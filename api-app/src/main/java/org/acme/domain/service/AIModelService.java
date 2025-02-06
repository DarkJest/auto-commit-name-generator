package org.acme.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.entity.EditedFile;
import org.acme.domain.repository.AIModelRepository;

import java.util.List;

@ApplicationScoped
public class AIModelService {
    private final AIModelRepository aiModelRepository;

    @Inject
    public AIModelService(AIModelRepository aiModelRepository) {
        this.aiModelRepository = aiModelRepository;
    }

    public String generateTitle(List<EditedFile> editedFiles) {
        return aiModelRepository.generateTitle(editedFiles);
    }
}