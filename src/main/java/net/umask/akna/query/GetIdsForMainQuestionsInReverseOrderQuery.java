package net.umask.akna.query;

import net.umask.akna.model.OrderedCaseQuestionAssociation;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

/**
 * User: Me
 * Date: 7-jan-2010
 * Time: 20:39:43
 */
public class GetIdsForMainQuestionsInReverseOrderQuery implements Query<List<Long>> {
    private Long caseId;

    public GetIdsForMainQuestionsInReverseOrderQuery(Long caseId) {

        this.caseId = caseId;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Long> execute(Session session) {
        return session.createCriteria(OrderedCaseQuestionAssociation.class)
                .add(eq("maternalCase.id",caseId))
                .addOrder(desc("id"))
                .setProjection(property("question.id"))
                .list();
    }
}
