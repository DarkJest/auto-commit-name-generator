package org.acme.application.commandHandlers;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.command.EditedFilesCommand;
import org.acme.domain.aggregate.CommitTitleGenerator;
import org.acme.domain.entity.EditedFile;
import org.acme.presentation.http.responses.GenerateCommitNameResponse;
import org.acme.shared.interfaces.commands.Command;
import org.acme.shared.interfaces.commands.CommandHandlerWithResult;
import org.acme.shared.interfaces.commands.CommandWithResult;

import java.util.List;

@ApplicationScoped
public class EditedFilesCommandHandler implements CommandHandlerWithResult<EditedFilesCommand, GenerateCommitNameResponse> {
    private final CommitTitleGenerator generator;

    @Inject
    public EditedFilesCommandHandler(CommitTitleGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public GenerateCommitNameResponse handle(CommandWithResult<GenerateCommitNameResponse> command) {
        try {
            EditedFilesCommand cmd = (EditedFilesCommand) command;
            List<EditedFile> editedFiles = cmd.getEditedFiles().stream().map(file -> new EditedFile(file, "EDITED")).toList();
            return new GenerateCommitNameResponse(generator.generate(editedFiles), "success");
        } catch (Exception e)
        {
            return new GenerateCommitNameResponse(
                    "Null",
                    "failed, error: " + e.getMessage()
            );
        }

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
