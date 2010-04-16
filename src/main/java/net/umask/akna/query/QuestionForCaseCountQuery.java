package net.umask.akna.query;

import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.rowCount;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 10:07:18
 */
public class QuestionForCaseCountQuery implements Query<Integer> {
    private long parentCaseId;

    public QuestionForCaseCountQuery(long parentCaseId) {
        this.parentCaseId = parentCaseId;
    }

    @Override
    public Integer execute(Session session) {
        return (Integer) QuestionsForCaseQuery.createCriteria(session, parentCaseId).setProjection(rowCount()).uniqueResult();
    }
}
