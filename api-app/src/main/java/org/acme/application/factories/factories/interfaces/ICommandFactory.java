package org.acme.application.factories.factories.interfaces;

import org.acme.presentation.http.requests.IRequest;
import org.acme.shared.interfaces.commands.Command;

public interface ICommandFactory {
    public Command createByRequest(IRequest request);
}
