package org.acme.shared.interfaces.commands;

public interface CommandHandler<T extends Command> extends BaseCommandHandler {
    void handle(Command command);
    Class<T> getCommandType();
}
