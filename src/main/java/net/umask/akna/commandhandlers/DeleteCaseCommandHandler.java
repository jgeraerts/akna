package net.umask.akna.commandhandlers;

import net.umask.akna.commands.DeleteCaseCommand;
import net.umask.akna.model.MaternalCase;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 10-jan-2010
 * Time: 7:49:29
 */
@Component
public class DeleteCaseCommandHandler extends AbstractCommandHandler<DeleteCaseCommand> {
    @Override
    public void handleMessage(DeleteCaseCommand command) {
        getRepository().remove(getRepository().get(MaternalCase.class, command.getId()));
    }
}
