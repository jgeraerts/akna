package net.umask.akna.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 29-dec-2009
 * Time: 17:46:51
 */
public class AnswerDTO implements Serializable {

    private Long id;
    private String answer;
    private String feedback;
    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
