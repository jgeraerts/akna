package net.umask.akna.query;

import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.dto.QuestionDTO;
import net.umask.akna.model.Answer;
import net.umask.akna.model.Question;
import net.umask.akna.service.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.transform.Transformers.aliasToBean;

import java.util.List;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 18:57:52
 */
public class QuestionDetailQuery implements Query<QuestionDetailQueryResult> {
    private Long questionId;

    public QuestionDetailQuery(Long questionId) {
        this.questionId = questionId;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public QuestionDetailQueryResult execute(Session session) {
        QuestionDTO dto =
                (QuestionDTO) session
                        .createCriteria(Question.class, "question")
                        .add(eq("question.id", questionId))
                        .createAlias("question.association", "association")
                        .createAlias("association.answer", "answer", CriteriaSpecification.LEFT_JOIN)
                        .createAlias("answer.parentQuestion", "parentQuestion", CriteriaSpecification.LEFT_JOIN)
                        .setProjection(
                                projectionList()
                                        .add(property("question.id"), "id")
                                        .add(property("question.question"), "question")
                                        .add(property("question.feedback"), "feedback")
                                        .add(property("association.maternalCase.id"), "maternalCaseId")
                                        .add(property("parentQuestion.id"), "parentQuestionId"))
                        .setResultTransformer(aliasToBean(QuestionDTO.class))
                        .uniqueResult();
        List<AnswerDTO> answers =
                session
                        .createCriteria(Answer.class)
                        .add(eq("parentQuestion.id", questionId))
                        .createAlias("association", "association", CriteriaSpecification.LEFT_JOIN)

                        .setProjection(projectionList()
                                .add(property("id"), "id")
                                .add(property("answer"), "answer")
                                .add(property("feedback"), "feedback")
                                .add(property("association.question.id"), "questionId")
                        )
                        .setResultTransformer(aliasToBean(AnswerDTO.class))
                        .addOrder(asc("id"))
                        .list();


        return new QuestionDetailQueryResult(dto, answers);
    }


}
