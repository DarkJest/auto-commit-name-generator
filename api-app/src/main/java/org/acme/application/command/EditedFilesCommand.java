package org.acme.application.command;

import org.acme.presentation.http.responses.GenerateCommitNameResponse;
import org.acme.shared.interfaces.commands.CommandWithResult;

import java.util.List;

public class EditedFilesCommand implements CommandWithResult<GenerateCommitNameResponse> {
    private final List<String> editedFiles;

    public EditedFilesCommand(List<String> editedFiles) {
        this.editedFiles = editedFiles;
    }

    public List<String> getEditedFiles() {
        return editedFiles;
    }

    @Override
    public Class<GenerateCommitNameResponse> getResultType() {
        return GenerateCommitNameResponse.class;
    }
}
