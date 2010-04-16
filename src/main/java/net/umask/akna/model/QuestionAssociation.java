package net.umask.akna.model;

import javax.persistence.*;

/**
 * User: Me
 * Date: 6-jan-2010
 * Time: 20:44:26
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "associationType", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("C")
class QuestionAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Question question;

    protected QuestionAssociation(Question question) {
        this.question = question;
    }

    protected QuestionAssociation() {
    }
}
