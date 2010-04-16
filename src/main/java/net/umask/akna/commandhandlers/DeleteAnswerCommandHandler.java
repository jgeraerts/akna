package net.umask.akna.commandhandlers;

import net.umask.akna.commands.DeleteAnswerCommand;
import net.umask.akna.model.Answer;
import net.umask.akna.model.Question;
import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 17:51:58
 */
@Component
public class DeleteAnswerCommandHandler extends AbstractCommandHandler<DeleteAnswerCommand> {


    @Autowired
    private Repository repository;

    @Override
    public void handleMessage(DeleteAnswerCommand command) {
        repository.get(Question.class, command.getQuestionid()).deleteAnswer(repository.get(Answer.class, command.getAnswerId()));

    }
}
