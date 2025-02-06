package org.acme.infrastructure.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.entity.EditedFile;
import org.acme.domain.repository.AIModelRepository;
import org.acme.infrastructure.adapters.interfaces.ApiCaller;
import org.acme.infrastructure.adapters.interfaces.ApiResponseParser;
import org.acme.infrastructure.adapters.interfaces.InputDataCreator;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;


import java.io.IOException;

@ApplicationScoped
public class GitHubAIModelApiAdapter implements AIModelRepository {
    private final InputDataCreator inputDataCreator;
    private final ApiCaller apiCaller;
    private final ApiResponseParser apiResponseParser;

    @Inject
    public GitHubAIModelApiAdapter(InputDataCreator inputDataCreator, ApiCaller apiCaller, ApiResponseParser apiResponseParser) {
        this.inputDataCreator = inputDataCreator;
        this.apiCaller = apiCaller;
        this.apiResponseParser = apiResponseParser;
    }

    @Override
    public String generateTitle(List<EditedFile> editedFiles) {
        String inputData = inputDataCreator.createInputData(editedFiles);
        try {
            String apiResponse = apiCaller.callExternalApi(inputData);
            return apiResponseParser.parseApiResponse(apiResponse);
        } catch (IOException e) {
            throw new RuntimeException("Error processing API call", e);
        }
    }
}