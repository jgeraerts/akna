package net.umask.akna.web.pages;

import com.google.common.collect.ImmutableMap;
import net.umask.akna.commands.CreateQuestionForAnswerCommand;
import net.umask.akna.commands.CreateQuestionForCaseCommand;
import net.umask.akna.web.Constants;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * User: Me
 * Date: 8-dec-2009
 * Time: 20:30:46
 */
@AuthorizeInstantiation({"ROLE_ADMIN"})
public class AddQuestionPage extends BasePage {

    private String question = "";

    public AddQuestionPage(final PageParameters params) {


        Form<AddQuestionPage> form;
        add(form = new Form<AddQuestionPage>("form", new CompoundPropertyModel<AddQuestionPage>(this)) {
            @Override
            protected void onSubmit() {
                if (params.containsKey(Constants.PARENT_CASE_ID)) {
                    MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new CreateQuestionForCaseCommand(params.getLong(Constants.PARENT_CASE_ID), question));
                    setResponsePage(ListQuestionsPage.class, new PageParameters(ImmutableMap.copyOf(params)));
                } else if (params.containsKey(Constants.PARENT_ANSWER_ID)) {
                    MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new CreateQuestionForAnswerCommand(params.getLong(Constants.PARENT_ANSWER_ID), question));
                    setResponsePage(QuestionDetailPage.class, new PageParameters(ImmutableMap.of(Constants.QUESTION_ID, params.get(Constants.QUESTION_ID))));

                }
            }
        });


        form.add(new TextArea("question").setRequired(true));


    }


}
