package net.umask.akna.commands;

/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 19:37:34
 */
public class UpdateFeedbackForQuestionCommand {
    private Long questionId;
    private String feedback;

    public UpdateFeedbackForQuestionCommand(Long questionId, String feedback) {
        this.questionId = questionId;
        this.feedback = feedback;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
