package net.umask.akna.query;

import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.model.Answer;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import static org.hibernate.criterion.Projections.*;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.transform.Transformers.aliasToBean;

/**
 * User: Me
 * Date: 29-dec-2009
 * Time: 17:53:13
 */
public class GetAnswerDTOQueryById implements Query<AnswerDTO> {
    private Long id;

    public GetAnswerDTOQueryById(Long id) {
        this.id = id;
    }

    @Override
    public AnswerDTO execute(Session session) {
        return (AnswerDTO) session
                .createCriteria(Answer.class, "answer")

                .add(eq("answer.id", id))
                .setProjection(
                        projectionList()
                                .add(property("answer.answer"), "answer")
                                .add(property("answer.id"), "id")
                                .add(alias(property("answer.parentQuestion.id"), "questionId"))

                )
                .setResultTransformer(aliasToBean(AnswerDTO.class))
                .uniqueResult();
    }
}
