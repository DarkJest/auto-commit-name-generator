package org.acme.presentation.http.responses;

public class GenerateCommitNameResponse {
    private final String generatedMessage;
    private final String message;

    public GenerateCommitNameResponse(String generatedMessage, String message) {
        this.generatedMessage = generatedMessage;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFiles() {
        return generatedMessage;
    }
}
