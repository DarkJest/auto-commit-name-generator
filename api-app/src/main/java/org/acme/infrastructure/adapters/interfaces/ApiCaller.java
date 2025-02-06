package org.acme.infrastructure.adapters.interfaces;

import java.io.IOException;

public interface ApiCaller {
    public String callExternalApi(String inputData) throws IOException;
}
