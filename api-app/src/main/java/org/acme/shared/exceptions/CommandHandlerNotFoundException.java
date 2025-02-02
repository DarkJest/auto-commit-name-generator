package org.acme.shared.exceptions;

public class CommandHandlerNotFoundException extends RuntimeException {
    public CommandHandlerNotFoundException(Class message) {
        super(String.valueOf(message));
    }
}
