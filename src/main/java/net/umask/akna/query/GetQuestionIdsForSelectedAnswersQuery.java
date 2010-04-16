package net.umask.akna.query;

import net.umask.akna.model.AnswerSubQuestionAssociation;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.in;

import java.util.Collections;
import java.util.List;

/**
 * User: Me
 * Date: 8-jan-2010
 * Time: 20:09:45
 */
public class GetQuestionIdsForSelectedAnswersQuery implements Query<List<Long>> {
    private List<Long> selectedAnswerIds;

    public GetQuestionIdsForSelectedAnswersQuery(List<Long> selectedAnswerIds) {
        this.selectedAnswerIds = selectedAnswerIds;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Long> execute(Session session) {
        if (selectedAnswerIds.size() == 0) {
            return Collections.emptyList();
        }
        return session.createCriteria(AnswerSubQuestionAssociation.class)
                .add(in("answer.id", selectedAnswerIds))
                .setProjection(property("question.id"))
                .list();
    }
}
