package net.umask.akna.web.pages;

import com.google.common.collect.ImmutableMap;
import net.umask.akna.commands.*;
import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.dto.QuestionDTO;
import net.umask.akna.web.Constants;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import wicket.contrib.tinymce.InPlaceEditComponent;
import wicket.contrib.tinymce.InPlaceSaveBehavior;
import wicket.contrib.tinymce.settings.TablePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;

import javax.swing.text.Position;
import java.util.List;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 18:46:41
 */
@AuthorizeInstantiation({"ROLE_ADMIN"})
public class QuestionDetailPage extends BasePage {

    private String newAnswer;

    public QuestionDetailPage(final PageParameters params) {
        final Long questionId = params.getLong(Constants.QUESTION_ID);
        final QuestionDetailLoadableDetachableModel model = new QuestionDetailLoadableDetachableModel(questionId);
        add(new Link("backLink") {

            @Override
            public void onClick() {
                QuestionDTO dto = model.getObject().getQuestion();
                if (dto.getMaternalCaseId() != null) {
                    setResponsePage(ListQuestionsPage.class, new PageParameters(ImmutableMap.of(Constants.PARENT_CASE_ID, dto.getMaternalCaseId())));
                } else if (dto.getParentQuestionId() != null) {
                    setResponsePage(QuestionDetailPage.class, new PageParameters(ImmutableMap.of(Constants.QUESTION_ID, dto.getParentQuestionId())));
                }
            }
        });
        InPlaceEditComponent inPlaceEditComponent=null;
        add(inPlaceEditComponent=new InPlaceEditComponent("question", new PropertyModel<String>(model, "question.question")) {

            @Override
            protected InPlaceSaveBehavior createSaveBehavior() {
                return new InPlaceSaveBehavior(){
                    @Override
                    protected String onSave(AjaxRequestTarget target, String newContent) {
                        MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new UpdateQuestionCommand(questionId,newContent));

                        return super.onSave(target, newContent);
                    }
                };
            }
        });
        TablePlugin tablePlugin = new TablePlugin();
        inPlaceEditComponent.getSettings().add(tablePlugin.getTableControls(), TinyMCESettings.Toolbar.first, TinyMCESettings.Position.after);
        inPlaceEditComponent.getSettings().setHorizontalResizing(true);
        add(new AjaxEditableMultiLineLabel<String>("questionFeedback", new PropertyModel<String>(model, "question.feedback")) {
            @Override
            protected void onModelChanged() {
                MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new UpdateFeedbackForQuestionCommand(questionId, model.getObject().getQuestion().getFeedback()));
            }

        });

        add(new ListView<AnswerDTO>("answers", new PropertyModel<List<AnswerDTO>>(model, "answers")) {
            @Override
            protected void populateItem(final ListItem<AnswerDTO> listItem) {
                listItem.add(new AjaxEditableMultiLineLabel<String>("answer", new PropertyModel<String>(listItem.getModel(), "answer")) {
                    @Override
                    protected void onModelChanged() {
                        MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new UpdateAnswerCommand(listItem.getModelObject().getId(), listItem.getModelObject().getAnswer()));
                    }
                });


                listItem.add(new AjaxEditableMultiLineLabel<String>("answerFeedback", new PropertyModel<String>(listItem.getModel(), "feedback")) {
                    @Override
                    protected void onModelChanged() {
                        MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new UpdateAnswerFeedBackCommand(listItem.getModelObject().getId(), listItem.getModelObject().getFeedback()));
                    }
                });


                listItem.add(new Link("deleteLink") {

                    @Override
                    public void onClick() {
                        MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new DeleteAnswerCommand(questionId, listItem.getModelObject().getId()));
                        setResponsePage(QuestionDetailPage.class, params);
                    }
                });

                listItem.add(new Link("editNewQuestionLink") {

                    @Override
                    public void onClick() {
                        if (listItem.getModelObject().getQuestionId() == null) {
                            setResponsePage(AddQuestionPage.class, new PageParameters(ImmutableMap.of(Constants.PARENT_ANSWER_ID, listItem.getModelObject().getId(), Constants.QUESTION_ID, questionId)));
                        } else {
                            setResponsePage(QuestionDetailPage.class, new PageParameters(ImmutableMap.of(Constants.QUESTION_ID, listItem.getModelObject().getQuestionId())));
                        }
                    }
                });

                listItem.add(new SimpleAttributeModifier("class", listItem.getIndex() % 2 == 0 ? "even" : "odd"));

            }
        });

        Form<QuestionDetailPage> form;
        add(form = new Form<QuestionDetailPage>("addAnswerForm", new CompoundPropertyModel<QuestionDetailPage>(this)) {
            @Override
            protected void onSubmit() {
                MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new AddAnswerToQuestionCommand(questionId, newAnswer));
                newAnswer = "";
            }
        });
        form.add(new RequiredTextField(("newAnswer")));
    }


}
