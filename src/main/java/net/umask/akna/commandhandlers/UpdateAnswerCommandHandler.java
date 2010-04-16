package net.umask.akna.commandhandlers;

import net.umask.akna.commands.UpdateAnswerCommand;
import net.umask.akna.model.Answer;
import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 16:56:03
 */

@Component
public class UpdateAnswerCommandHandler extends AbstractCommandHandler<UpdateAnswerCommand> {

    @Autowired
    private Repository repository;

    @Override
    public void handleMessage(UpdateAnswerCommand command) {
        repository.get(Answer.class, command.getId()).setAnswer(command.getAnswer());

    }
}
