package net.umask.akna.commands;

/**
 * User: Me
 * Date: 9-jan-2010
 * Time: 9:03:02
 */
public class DeleteCaseCommand {
    private Long id;

    public DeleteCaseCommand(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
