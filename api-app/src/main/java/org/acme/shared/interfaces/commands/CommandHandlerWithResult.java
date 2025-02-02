package org.acme.shared.interfaces.commands;

public interface CommandHandlerWithResult<T extends CommandWithResult<R>, R> extends BaseCommandHandler {
    R handle(CommandWithResult<R> command);
    Class<T> getCommandType();
}