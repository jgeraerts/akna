package net.umask.akna.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * User: Me
 * Date: 25-nov-2009
 * Time: 20:40:45
 */
@Entity
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String question;
    @Column(columnDefinition = "TEXT")
    private String feedback;
    @OneToMany(mappedBy = "parentQuestion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cascade(value = {org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.ALL})
    private List<Answer> answers = Lists.newArrayList();

    @OneToOne(mappedBy = "question")
    private QuestionAssociation association;


    public Question() {
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


    public Question withQuestion(String s) {
        this.question = s;
        return this;
    }

    public Question withId(Long id) {
        this.id = id;
        return this;
    }

    public List<Answer> getAnswers() {
        return ImmutableList.copyOf(answers);
    }


    public void addAnswer(String answer) {
        answers.add(new Answer(this, answer));
    }

    public void deleteAnswer(Answer answer) {
        answers.remove(answer);
    }

    public Question(String question) {
        this.question = question;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
