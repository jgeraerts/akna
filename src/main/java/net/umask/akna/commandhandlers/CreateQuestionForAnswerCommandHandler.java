package net.umask.akna.commandhandlers;

import net.umask.akna.commands.CreateQuestionForAnswerCommand;
import net.umask.akna.model.Answer;
import net.umask.akna.model.AnswerSubQuestionAssociation;
import net.umask.akna.model.Question;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 22:23:44
 */

@Component
public class CreateQuestionForAnswerCommandHandler extends AbstractCommandHandler<CreateQuestionForAnswerCommand> {
    @Override
    public void handleMessage(CreateQuestionForAnswerCommand command) {
        getRepository().save(new AnswerSubQuestionAssociation(getRepository().get(Answer.class, command.getAnswerId()), new Question(command.getQuestion())));
    }
}
