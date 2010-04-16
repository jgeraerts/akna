package net.umask.akna.commandhandlers;

import net.umask.akna.commands.UpdateOrderCommand;
import net.umask.akna.model.OrderedCaseQuestionAssociation;
import net.umask.akna.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Me
 * Date: 6-jan-2010
 * Time: 22:02:11
 */
@Component
public class UpdateOrderCommandHandler extends AbstractCommandHandler<UpdateOrderCommand> {

    @Autowired
    private Repository repository;

    @Override
    public void handleMessage(UpdateOrderCommand command) {
        for (Long id : command.getOrderedIds()) {
            repository.get(OrderedCaseQuestionAssociation.class, id).updateOrder(command.getOrderedIds().indexOf(id));
        }

    }
}
