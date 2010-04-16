package net.umask.akna.commandhandlers;

import net.umask.akna.commands.UpdateQuestionCommand;
import net.umask.akna.model.Question;
import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 20:58:23
 */
@Component
public class UpdateQuestionCommandHandler extends AbstractCommandHandler<UpdateQuestionCommand> {

    @Autowired
    private Repository repository;

    @Override
    public void handleMessage(UpdateQuestionCommand command) {
        repository.get(Question.class, command.getQuestionId()).setQuestion(command.getQuestion());


    }
}
