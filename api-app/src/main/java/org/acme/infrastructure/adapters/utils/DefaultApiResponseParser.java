package org.acme.infrastructure.adapters.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.infrastructure.adapters.interfaces.ApiResponseParser;

import java.io.IOException;

@ApplicationScoped
public class DefaultApiResponseParser implements ApiResponseParser {
    private final ObjectMapper mapper;

    @Inject
    public DefaultApiResponseParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String parseApiResponse(String apiResponse) throws IOException {
        JsonNode jsonNode = mapper.readTree(apiResponse);
        return jsonNode.get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText()
                .trim();
    }
}
