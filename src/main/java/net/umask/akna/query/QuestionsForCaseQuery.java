package net.umask.akna.query;

import net.umask.akna.dto.QuestionDTO;
import net.umask.akna.model.OrderedCaseQuestionAssociation;
import net.umask.akna.service.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.transform.Transformers.aliasToBean;

import java.util.List;

/**
 * User: Me
 * Date: 3-jan-2010
 * Time: 9:54:18
 */
public class QuestionsForCaseQuery implements Query<List<QuestionDTO>> {
    private long parentCaseId;
    private int first;
    private int count;

    public QuestionsForCaseQuery(long parentCaseId, int first, int count) {

        this.parentCaseId = parentCaseId;
        this.first = first;
        this.count = count;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<QuestionDTO> execute(Session session) {
        return createCriteria(session, parentCaseId)
                .setProjection(
                        projectionList()
                                .add(property("id"), "associationId")
                                .add(property("question.id"), "id")
                                .add(property("question.question"), "question"))
                .setResultTransformer(aliasToBean(QuestionDTO.class))
                .setFirstResult(first)
                .setMaxResults(count)
                .list();


    }

    public static Criteria createCriteria(Session session, long parentCaseId) {
        return session.createCriteria(OrderedCaseQuestionAssociation.class)
                .add(eq("maternalCase.id", parentCaseId))
                .createAlias("question", "question");
    }
}
