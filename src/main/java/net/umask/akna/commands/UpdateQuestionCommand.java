package net.umask.akna.commands;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 19:23:31
 */
public class UpdateQuestionCommand {
    private Long questionId;
    private String question;

    public UpdateQuestionCommand(Long questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }
}
