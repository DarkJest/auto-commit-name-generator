package org.acme.application.commandHandlers;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.command.EditedFilesCommand;
import org.acme.presentation.http.responses.GenerateCommitNameResponse;
import org.acme.shared.interfaces.commands.Command;
import org.acme.shared.interfaces.commands.CommandHandlerWithResult;
import org.acme.shared.interfaces.commands.CommandWithResult;

@ApplicationScoped
public class EditedFilesCommandHandler implements CommandHandlerWithResult<EditedFilesCommand, GenerateCommitNameResponse> {


    @Inject
    public EditedFilesCommandHandler()
    {

    }

    @Override
    public GenerateCommitNameResponse handle(CommandWithResult<GenerateCommitNameResponse> command) {
        return new GenerateCommitNameResponse("todo", "todo");
    }

    @Override
    public Class<EditedFilesCommand> getCommandType() {
        return EditedFilesCommand.class;
    }

    @Override
    public boolean supports(Command command) {
        return EditedFilesCommand.class == command.getClass();
    }
}
