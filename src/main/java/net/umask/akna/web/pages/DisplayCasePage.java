package net.umask.akna.web.pages;

import net.umask.akna.web.MaternalWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by IntelliJ IDEA.
 * User: JoGeraerts
 * Date: 24-aug-2010
 */
public class DisplayCasePage extends BasePage {

    public DisplayCasePage(final Long caseId){
        final MaternalCaseLoadableDetachableModel maternalCaseLoadableDetachableModel = new MaternalCaseLoadableDetachableModel(caseId);
        add(new Label("title", new PropertyModel<String>(maternalCaseLoadableDetachableModel,"title")));
        add(new MultiLineLabel("body", new PropertyModel<String>(maternalCaseLoadableDetachableModel,"body")).setEscapeModelStrings(false));
        add(new Link("nextLink"){
            @Override
            public void onClick() {
                MaternalWebSession.get().initializeQuestionStack(caseId);
                setResponsePage(new AskQuestionPage(maternalCaseLoadableDetachableModel));
            }
        });
    }

}
