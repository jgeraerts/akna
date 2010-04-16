package net.umask.akna.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 25-dec-2009
 * Time: 11:24:29
 */
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Question parentQuestion;

    @OneToOne(mappedBy = "answer", cascade = CascadeType.ALL)
    private AnswerSubQuestionAssociation association;

    private String answer;
    @Column(columnDefinition = "TEXT")
    private String feedback;

    public String getAnswer() {
        return answer;
    }

    public Long getId() {
        return id;
    }

    public Answer() {
    }

    public Answer(Question parentQuestion, String answer) {
        this.parentQuestion = parentQuestion;
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Answer withId(Long id) {
        this.id = id;
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
