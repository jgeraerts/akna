package net.umask.akna.commands;

import java.util.List;

/**
 * User: Me
 * Date: 6-jan-2010
 * Time: 21:57:46
 */
public class UpdateOrderCommand {
    private List<Long> orderedIds;

    public UpdateOrderCommand(List<Long> orderedIds) {
        this.orderedIds = orderedIds;
    }

    public List<Long> getOrderedIds() {
        return orderedIds;
    }
}
