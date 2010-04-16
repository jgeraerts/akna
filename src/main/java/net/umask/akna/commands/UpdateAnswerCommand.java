package net.umask.akna.commands;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 16:49:08
 */
public class UpdateAnswerCommand {
    private final Long id;
    private final String answer;

    public UpdateAnswerCommand(Long id, String answer) {
        this.id = id;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }
}
