package org.acme.infrastructure.adapters.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import okhttp3.*;
import org.acme.infrastructure.adapters.interfaces.ApiCaller;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@ApplicationScoped
public class DefaultApiCaller implements ApiCaller {
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final String apiUrl;
    private final String apiKey;

    @Inject
    public DefaultApiCaller(
            ObjectMapper mapper,
            @ConfigProperty(name = "app.adapters.github.api.url") String apiUrl,
            @ConfigProperty(name = "app.adapters.github.key") String apiKey
    ) {
        this.client = new OkHttpClient();
        this.mapper = mapper;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Override
    public String callExternalApi(String inputData) throws IOException {
        ObjectNode jsonNode = mapper.createObjectNode();
        ObjectNode userMessage = mapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", inputData);
        jsonNode.putArray("messages").add(userMessage);
        jsonNode.put("model", "gpt-4o");
        jsonNode.put("temperature", 1);
        jsonNode.put("max_tokens", 4096);
        jsonNode.put("top_p", 1);

        String jsonBody = mapper.writeValueAsString(jsonNode);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("api-key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed: " + response.code() + " - " + response.body().string());
            }
            assert response.body() != null;
            return response.body().string();
        }
    }
}
