package net.umask.akna.commandhandlers;

import net.umask.akna.commands.UpdateFeedbackForQuestionCommand;
import net.umask.akna.model.Question;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 19:38:16
 */

@Component
public class UpdateFeedbackForQuestionCommandHandler extends AbstractCommandHandler<UpdateFeedbackForQuestionCommand> {
    @Override
    public void handleMessage(UpdateFeedbackForQuestionCommand command) {
        getRepository().get(Question.class, command.getQuestionId()).setFeedback(command.getFeedback());
    }
}
