package net.umask.akna.web.pages;

import net.umask.akna.query.FeedbackForSelectedAnswers;
import net.umask.akna.query.FeedbackQueryResult;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.List;


/**
 * User: Me
 * Date: 12-feb-2010
 * Time: 20:58:33
 */
public class FeedbackPage extends BasePage {
    public FeedbackPage(Long currentQuestionId, List<Long> selectedAnswerIds) {

        FeedbackQueryResult r = MaternalWicketApplication.get().getQueryService().executeQuery(new FeedbackForSelectedAnswers(currentQuestionId, selectedAnswerIds));
        add(new ListView<String>("questionFeedback", r.getQuestionFeedback()) {

            @Override
            protected void populateItem(ListItem<String> listItem) {
                listItem.add(new Label("feedback", listItem.getModel()));
            }
        });
        add(new ListView<String>("answerFeedback", r.getAnswerFeedback()) {

            @Override
            protected void populateItem(ListItem<String> listItem) {
                listItem.add(new Label("feedback", listItem.getModel()));
            }
        });

        add(new Link("next") {
            @Override
            public void onClick() {
                setResponsePage(new AskQuestionPage());
            }
        });
    }
}
