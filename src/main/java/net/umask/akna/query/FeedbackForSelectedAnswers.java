package net.umask.akna.query;

import net.umask.akna.model.Answer;
import net.umask.akna.model.Question;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.property;
import org.hibernate.criterion.Restrictions;
import static org.hibernate.criterion.Restrictions.idEq;
import static org.hibernate.criterion.Restrictions.in;

import java.util.List;

/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 21:28:34
 */
public class FeedbackForSelectedAnswers implements Query<FeedbackQueryResult> {
    private Long currentQuestionId;
    private List<Long> selectedAnswerIds;

    public FeedbackForSelectedAnswers(Long currentQuestionId, List<Long> selectedAnswerIds) {
        this.currentQuestionId = currentQuestionId;

        this.selectedAnswerIds = selectedAnswerIds;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public FeedbackQueryResult execute(Session session) {
        List<String> f = session.createCriteria(Answer.class, "a")
                .add(in("a.id", selectedAnswerIds))
                .add(Restrictions.isNotNull("feedback"))
                .setProjection(property("feedback"))
                .list();

        List<String> qf = session.createCriteria(Question.class)
                .add(idEq(currentQuestionId))
                .add(Restrictions.isNotNull("feedback"))
                .setProjection(property("feedback"))
                .list();
        return new FeedbackQueryResult(qf, f);
    }
}
