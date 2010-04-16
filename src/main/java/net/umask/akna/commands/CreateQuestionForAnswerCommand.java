package net.umask.akna.commands;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 22:22:36
 */
public class CreateQuestionForAnswerCommand {
    private long answerId;
    private String question;

    public CreateQuestionForAnswerCommand(long answerId, String question) {
        this.answerId = answerId;
        this.question = question;
    }

    public long getAnswerId() {
        return answerId;
    }

    public String getQuestion() {
        return question;
    }
}
