package net.umask.akna.commandhandlers;

import net.umask.akna.commands.SaveOrUpdateMaternalCaseCommand;
import net.umask.akna.model.MaternalCase;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 10-jan-2010
 * Time: 7:49:54
 */
@Component
public class SaveOrUpdateMaternalCaseCommandHandler extends AbstractCommandHandler<SaveOrUpdateMaternalCaseCommand> {

    @Override
    public void handleMessage(SaveOrUpdateMaternalCaseCommand command) {
        if (command.getId() == null) {
            getRepository().save(new MaternalCase(command.getTitle(), command.getBody()));
        } else {
            getRepository().get(MaternalCase.class, command.getId()).updateTitleAndBody(command.getTitle(), command.getBody());
        }
    }
}
