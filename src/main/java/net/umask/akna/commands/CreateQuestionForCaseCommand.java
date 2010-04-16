package net.umask.akna.commands;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 10:25:28
 */
public class CreateQuestionForCaseCommand {
    private Long parentCaseId;
    private String question;

    public CreateQuestionForCaseCommand(Long parentCaseId, String question) {
        this.parentCaseId = parentCaseId;
        this.question = question;
    }

    public Long getParentCaseId() {
        return parentCaseId;
    }

    public String getQuestion() {
        return question;
    }
}
