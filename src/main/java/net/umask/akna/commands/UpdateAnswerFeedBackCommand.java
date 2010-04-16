package net.umask.akna.commands;

/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 20:44:35
 */
public class UpdateAnswerFeedBackCommand {
    private Long id;
    private String feedback;

    public UpdateAnswerFeedBackCommand(Long id, String feedback) {
        this.id = id;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }
}
