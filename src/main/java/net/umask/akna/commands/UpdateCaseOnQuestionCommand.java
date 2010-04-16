package net.umask.akna.commands;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 22:07:45
 */
public class UpdateCaseOnQuestionCommand {
    private Long questionId;
    private Long caseId;

    public UpdateCaseOnQuestionCommand(Long questionId, Long caseId) {
        this.questionId = questionId;
        this.caseId = caseId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public Long getQuestionId() {
        return questionId;
    }
}
