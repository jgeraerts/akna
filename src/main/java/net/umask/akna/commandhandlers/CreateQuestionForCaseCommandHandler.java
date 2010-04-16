package net.umask.akna.commandhandlers;

import net.umask.akna.commands.CreateQuestionForCaseCommand;
import net.umask.akna.model.MaternalCase;
import net.umask.akna.model.OrderedCaseQuestionAssociation;
import net.umask.akna.model.Question;
import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 10:55:15
 */
@Component
public class CreateQuestionForCaseCommandHandler extends AbstractCommandHandler<CreateQuestionForCaseCommand> {

    @Autowired
    private Repository repository;

    @Override
    public void handleMessage(CreateQuestionForCaseCommand command) {
        MaternalCase mCase = repository.get(MaternalCase.class, command.getParentCaseId());
        repository.save(new OrderedCaseQuestionAssociation(mCase, new Question(command.getQuestion())));


    }
}
