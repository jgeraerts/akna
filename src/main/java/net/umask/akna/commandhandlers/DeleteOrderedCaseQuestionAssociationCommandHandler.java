package net.umask.akna.commandhandlers;

import net.umask.akna.commands.DeleteOrderedCaseQuestionAssociationCommand;
import net.umask.akna.model.OrderedCaseQuestionAssociation;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 12:05:49
 */
@Component
public class DeleteOrderedCaseQuestionAssociationCommandHandler extends AbstractCommandHandler<DeleteOrderedCaseQuestionAssociationCommand> {
    @Override
    public void handleMessage(DeleteOrderedCaseQuestionAssociationCommand associationCommandOrderedCase) {
        getRepository().remove(getRepository().get(OrderedCaseQuestionAssociation.class, associationCommandOrderedCase.getId()));
    }
}
