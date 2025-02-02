package org.acme.infrastructure.bus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.acme.shared.interfaces.commands.*;

import java.lang.reflect.ParameterizedType;

@ApplicationScoped
public class CommandBus {
    private final Instance<BaseCommandHandler> handlers;

    @Inject
    public CommandBus(Instance<BaseCommandHandler> handlers) {
        this.handlers = handlers;
    }

    public <R> R dispatch(CommandWithResult<R> command) throws Exception {
        return this.handlers.stream()
                .filter(h -> h instanceof CommandHandlerWithResult && supportsCommand((CommandHandlerWithResult<?, ?>) h, command))
                .map(h -> (CommandHandlerWithResult<?, R>) h)
                .findFirst()
                .map(h -> {
                    try {
                        return h.handle(command);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("No suitable handler found"));
    }

    public void dispatch(Command command) throws Exception {
        this.handlers.stream()
                .filter(h -> h instanceof CommandHandler && supportsCommand((CommandHandler<?>) h, command))
                .map(h -> (CommandHandler<? extends Command>) h)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No suitable handler found"))
                .handle(command);
    }

    private boolean supportsCommand(BaseCommandHandler handler, Command command) {
        return handler.supports(command);
    }

    private Class<?> getCommandType(BaseCommandHandler handler) {
        return (Class<?>) ((ParameterizedType) handler.getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }
}