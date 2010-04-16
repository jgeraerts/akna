package net.umask.akna.dto;

import java.io.Serializable;

/**
 * User: Me
 * Date: 6-jan-2010
 * Time: 21:34:05
 */
public class OrderedQuestionDTO implements Serializable {

    private Long id;
    private String question;
    private String maternalCase;

    public String getQuestion() {
        return question;
    }


    public String getMaternalCase() {
        return maternalCase;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setMaternalCase(String maternalCase) {
        this.maternalCase = maternalCase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
