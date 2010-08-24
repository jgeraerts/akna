package net.umask.akna.web.pages;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.query.QuestionDetailQueryResult;
import net.umask.akna.web.MaternalWebSession;
import net.umask.akna.web.components.HtmlSafeMultiLineLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.EmptyStackException;


/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 20:58:33
 */
public class FeedbackPage extends BasePage {


    public FeedbackPage(IModel<QuestionDetailQueryResult> currentQuestion, final IModel<MaternalCaseDTO> maternalCaseLoadableDetachableModel) {

        add(new Label("title", new PropertyModel<String>(maternalCaseLoadableDetachableModel, "title")));
        add(new HtmlSafeMultiLineLabel("feedback", new PropertyModel(currentQuestion, "question.feedback")));

        add(new Link("next") {
            @Override
            public void onClick() {
                try {
                    MaternalWebSession.get().nextQuestionId();
                    setResponsePage(new AskQuestionPage(maternalCaseLoadableDetachableModel));
                } catch (EmptyStackException e){
                    setResponsePage(HomePage.class);
                }
            }
        });

    }
}
