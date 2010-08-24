package net.umask.akna.query;

import net.umask.akna.service.Query;
import net.umask.akna.model.Answer;
import net.umask.akna.model.Question;

import java.util.List;

import org.hibernate.Session;
import static org.hibernate.criterion.Restrictions.in;
import static org.hibernate.criterion.Restrictions.idEq;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Projections.rowCount;

/**
 * User: Me
 * Date: 15-mei-2010
 * Time: 13:40:38
 */
public class HasFeedBackForSelectedAnswers implements Query<Boolean> {

    private List<Long> selectedAnswerIds;

    public HasFeedBackForSelectedAnswers(List<Long> selectedAnswerIds) {

        this.selectedAnswerIds = selectedAnswerIds;
    }

    @Override
    public Boolean execute(Session session) {
        return (Long) session.createCriteria(Answer.class, "a")
                .add(in("a.id", selectedAnswerIds))
                .add(Restrictions.isNotNull("feedback"))
                .setProjection(rowCount())
                .uniqueResult() > 0;
    }
}
