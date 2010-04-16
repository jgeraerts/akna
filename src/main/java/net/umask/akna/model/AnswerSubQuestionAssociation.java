package net.umask.akna.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * User: Me
 * Date: 2-jan-2010
 * Time: 11:15:01
 */

@Entity
@DiscriminatorValue("A")
public class AnswerSubQuestionAssociation extends QuestionAssociation {


    @OneToOne
    private Answer answer;


    public AnswerSubQuestionAssociation(Answer answer, Question question) {
        super(question);
        this.answer = answer;

    }

    private AnswerSubQuestionAssociation() {
    }

    }
