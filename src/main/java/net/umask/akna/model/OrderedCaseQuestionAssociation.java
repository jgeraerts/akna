package net.umask.akna.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * User: Me
 * Date: 2-jan-2010
 * Time: 11:04:29
 */

@Entity
@DiscriminatorValue("B")
public class OrderedCaseQuestionAssociation extends QuestionAssociation {


    @OneToOne
    private MaternalCase maternalCase;

    @Column(name = "ORDERINDEX")
    private Integer order;

    private OrderedCaseQuestionAssociation() {
    }

    public OrderedCaseQuestionAssociation(MaternalCase maternalCase, Question question) {
        super(question);
        this.maternalCase = maternalCase;
    }

    public void updateOrder(Integer order) {
        this.order = order;
    }
}
