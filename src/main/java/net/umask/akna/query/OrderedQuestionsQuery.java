package net.umask.akna.query;

import net.umask.akna.dto.OrderedQuestionDTO;
import net.umask.akna.model.OrderedCaseQuestionAssociation;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.transform.Transformers.aliasToBean;

import java.util.List;

/**
 * User: Me
 * Date: 6-jan-2010
 * Time: 21:36:59
 */
public class OrderedQuestionsQuery implements Query<List<OrderedQuestionDTO>> {
    @SuppressWarnings({"unchecked"})
    @Override
    public List<OrderedQuestionDTO> execute(Session session) {
        return session.createCriteria(OrderedCaseQuestionAssociation.class)
                .createAlias("question", "question")
                .createAlias("maternalCase", "maternalCase")
                .addOrder(asc("order"))
                .setProjection(
                        projectionList()
                                .add(property("question.question"), "question")
                                .add(property("maternalCase.title"), "maternalCase")
                                .add(property("id"), "id")
                )
                .setResultTransformer(aliasToBean(OrderedQuestionDTO.class))
                .list();

    }
}
