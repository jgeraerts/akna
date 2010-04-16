package net.umask.akna.commands;

/**
 * User: Me
 * Date: 10-jan-2010
 * Time: 7:45:41
 */
public class SaveOrUpdateMaternalCaseCommand {
    private Long id;
    private String title;
    private String body;

    public SaveOrUpdateMaternalCaseCommand(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
