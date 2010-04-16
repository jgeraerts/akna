package net.umask.akna.web.pages;

import net.umask.akna.query.QuestionDetailQuery;
import net.umask.akna.query.QuestionDetailQueryResult;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 18:55:18
 */
public class QuestionDetailLoadableDetachableModel extends LoadableDetachableModel<QuestionDetailQueryResult> {
    private Long questionId;

    public QuestionDetailLoadableDetachableModel(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    protected QuestionDetailQueryResult load() {
        return MaternalWicketApplication.get().getQueryService().executeQuery(new QuestionDetailQuery(questionId));
    }
}
