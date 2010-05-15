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
    private Long currentQuestionId;
    private List<Long> selectedAnswerIds;

    public HasFeedBackForSelectedAnswers(Long currentQuestionId, List<Long> selectedAnswerIds) {
        this.currentQuestionId = currentQuestionId;
        this.selectedAnswerIds = selectedAnswerIds;
    }

    @Override
    public Boolean execute(Session session) {
        Long d = (Long) session.createCriteria(Answer.class, "a")
                .add(in("a.id", selectedAnswerIds))
                .add(Restrictions.isNotNull("feedback"))
                .setProjection(rowCount())
                .uniqueResult();

        Long e = (Long) session.createCriteria(Question.class)
                .add(idEq(currentQuestionId))
                .add(Restrictions.isNotNull("feedback"))
                .setProjection(rowCount())
                .uniqueResult();

        return e > 0 || d > 0;
    }
}
