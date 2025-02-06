package org.acme.infrastructure.adapters.interfaces;

import java.io.IOException;

public interface ApiResponseParser {
    public String parseApiResponse(String apiResponse) throws IOException;
}
