package net.umask.akna.commandhandlers;

import net.umask.akna.commands.AddAnswerToQuestionCommand;
import net.umask.akna.model.Question;
import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 16:04:12
 */
@Component
public class AddAnswerToQuestionCommandHandler extends AbstractCommandHandler<AddAnswerToQuestionCommand> {

    @Autowired
    private Repository repository;

    @Override
    public void handleMessage(AddAnswerToQuestionCommand command) {
        repository.get(Question.class, command.getParentQuestionId()).addAnswer(command.getAnswer());

    }
}
