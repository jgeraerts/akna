package net.umask.akna.dto;

import java.io.Serializable;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 18:59:31
 */
public class QuestionDTO implements Serializable {

    Long id;
    String question;
    String feedback;
    Long associationId;
    Long maternalCaseId;
    Long parentAnswerId;
    private Long parentQuestionId;

    public Long getMaternalCaseId() {
        return maternalCaseId;
    }

    public void setMaternalCaseId(Long maternalCaseId) {
        this.maternalCaseId = maternalCaseId;
    }

    public Long getParentAnswerId() {
        return parentAnswerId;
    }

    public void setParentAnswerId(Long parentAnswerId) {
        this.parentAnswerId = parentAnswerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public Long getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(Long parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
