package org.acme.shared.interfaces.commands;

public interface CommandWithResult<R> extends Command {
    Class<R> getResultType();
}
