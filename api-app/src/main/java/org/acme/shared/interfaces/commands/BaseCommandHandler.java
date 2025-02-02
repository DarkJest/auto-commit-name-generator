package org.acme.shared.interfaces.commands;

public interface BaseCommandHandler {
    boolean supports(Command command);
}