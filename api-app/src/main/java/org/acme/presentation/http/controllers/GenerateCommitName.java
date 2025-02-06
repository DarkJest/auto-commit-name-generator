package org.acme.presentation.http.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.factories.factories.interfaces.ICommandFactory;
import org.acme.infrastructure.bus.CommandBus;
import org.acme.presentation.http.requests.EditedFilesRequest;
import org.acme.presentation.http.responses.GenerateCommitNameResponse;
import org.acme.shared.interfaces.commands.CommandWithResult;

@Path("/generate-commit-name")
public class GenerateCommitName {
    private final CommandBus cmdBus;
    private final ICommandFactory commandFactory;

    @Inject
    public GenerateCommitName (CommandBus cmdBus, ICommandFactory commandFactory)
    {
        this.cmdBus = cmdBus;
        this.commandFactory = commandFactory;
    }

    @POST
    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generate(EditedFilesRequest editedFilesJson) throws Exception {
        CommandWithResult<GenerateCommitNameResponse> cmd = (CommandWithResult<GenerateCommitNameResponse>) commandFactory.createByRequest(editedFilesJson);
        GenerateCommitNameResponse result = cmdBus.dispatch(cmd);

        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }

}