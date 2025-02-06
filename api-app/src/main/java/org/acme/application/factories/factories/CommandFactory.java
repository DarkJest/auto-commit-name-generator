package org.acme.application.factories.factories;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.application.command.EditedFilesCommand;
import org.acme.application.factories.factories.interfaces.ICommandFactory;
import org.acme.presentation.http.requests.EditedFilesRequest;
import org.acme.presentation.http.requests.IRequest;
import org.acme.shared.interfaces.commands.Command;

@ApplicationScoped
public class CommandFactory implements ICommandFactory {
    @Override
    public Command createByRequest(IRequest request) {
        if (request instanceof EditedFilesRequest) {
            return new EditedFilesCommand( ((EditedFilesRequest) request).getEditedFiles());
        }
        return null;
    }
}
