package net.umask.akna.commands;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 17:12:01
 */
public class DeleteAnswerCommand {
    private Long questionid;
    private Long answerId;

    public DeleteAnswerCommand(Long questionId, Long answerId) {
        this.questionid = questionId;
        this.answerId = answerId;
    }

    public Long getQuestionid() {
        return questionid;
    }

    public Long getAnswerId() {
        return answerId;
    }
}
