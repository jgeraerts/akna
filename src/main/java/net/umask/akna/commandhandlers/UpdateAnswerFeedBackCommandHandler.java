package net.umask.akna.commandhandlers;

import net.umask.akna.commands.UpdateAnswerFeedBackCommand;
import net.umask.akna.model.Answer;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 20:45:04
 */

@Component
public class UpdateAnswerFeedBackCommandHandler extends AbstractCommandHandler<UpdateAnswerFeedBackCommand> {
    @Override
    public void handleMessage(UpdateAnswerFeedBackCommand command) {
        getRepository().get(Answer.class, command.getId()).setFeedback(command.getFeedback());
    }
}
