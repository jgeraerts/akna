package net.umask.akna.commands;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 16:02:57
 */
public class AddAnswerToQuestionCommand {
    private Long parentQuestionId;
    private String answer;

    public AddAnswerToQuestionCommand(Long parentQuestionId, String answer) {
        this.parentQuestionId = parentQuestionId;
        this.answer = answer;
    }

    public Long getParentQuestionId() {
        return parentQuestionId;
    }

    public String getAnswer() {
        return answer;
    }
}
