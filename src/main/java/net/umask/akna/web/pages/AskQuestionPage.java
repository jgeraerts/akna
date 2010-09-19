package net.umask.akna.web.pages;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.query.GetQuestionIdsForSelectedAnswersQuery;
import net.umask.akna.query.HasFeedBackForSelectedAnswers;
import net.umask.akna.query.QuestionDetailQueryResult;
import net.umask.akna.web.MaternalWebSession;
import net.umask.akna.web.MaternalWicketApplication;
import net.umask.akna.web.components.HtmlSafeMultiLineLabel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;


/**
 * User: Me
 * Date: 7-jan-2010
 * Time: 20:20:39
 */
public class AskQuestionPage extends BasePage {
    private Long currentQuestionId;
    private IModel<QuestionDetailQueryResult> currentQuestion;

    private List<AnswerDTOWrapper> wrappedAnswers;
    private boolean formSubmitted = false;

    public AskQuestionPage(final IModel<MaternalCaseDTO> maternalCaseLoadableDetachableModel) {
        currentQuestionId = getNextQuestionId();
        currentQuestion = getCurrentQuestion();
        wrappedAnswers = getWrappedAnswers();

        add(new Label("title", new PropertyModel(maternalCaseLoadableDetachableModel, "title")));
        add(new HtmlSafeMultiLineLabel("question", new PropertyModel<String>(currentQuestion, "question.question")));
        final WebMarkupContainer answerContainer = new WebMarkupContainer("answerContainer");
        add(answerContainer);
        answerContainer.setOutputMarkupId(true);

        Form form;
        answerContainer.add(form = new Form("answerForm"));

        ListView<AnswerDTOWrapper> listView;
        form.add(listView = new ListView<AnswerDTOWrapper>("list", new WrappedAnswersModel()) {

            @Override
            protected void populateItem(final ListItem<AnswerDTOWrapper> answerDTOListItem) {

                answerDTOListItem.add(new CheckBox("check", new PropertyModel<Boolean>(answerDTOListItem.getModel(), "selected")));
                answerDTOListItem.add(new Label("answer", new PropertyModel<Boolean>(answerDTOListItem.getModel(), "answer.answer")));
                answerDTOListItem.add(new HtmlSafeMultiLineLabel("feedback", new PropertyModel<String>(answerDTOListItem.getModel(), "answer.feedback")) {
                    @Override
                    public boolean isVisible() {
                        return answerDTOListItem.getModelObject().isSelected();
                    }
                });

            }
        });
        listView.setReuseItems(true);
        form.add(new AjaxButton("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                List<Long> selectedAnswerIds =
                        ImmutableList.copyOf(
                                transform(
                                        filter(
                                                wrappedAnswers,
                                                new IsSelectedPredicate()),
                                        new ExtractIdFunction()));
                MaternalWebSession.get().popOffCurrentQuestion();

                if (selectedAnswerIds.size() > 0) {
                    MaternalWebSession.get().addNewQuestionIds(MaternalWicketApplication.get().getQueryService().executeQuery(new GetQuestionIdsForSelectedAnswersQuery(selectedAnswerIds)));
                    if (MaternalWicketApplication.get().getQueryService().executeQuery(new HasFeedBackForSelectedAnswers(selectedAnswerIds))) {
                        formSubmitted = true;
                        target.addComponent(answerContainer);
                        return;
                    }
                }
                forwardToNextPage(maternalCaseLoadableDetachableModel);
            }


            @Override
            public boolean isVisible() {
                return !formSubmitted;
            }
        });
        answerContainer.add(new Link("nextLink") {
            @Override
            public void onClick() {

                forwardToNextPage(maternalCaseLoadableDetachableModel);
            }

            @Override
            public boolean isVisible() {
                return formSubmitted;
            }

        });
    }


    private ImmutableList<AnswerDTOWrapper> getWrappedAnswers() {
        return ImmutableList.copyOf(transform(currentQuestion.getObject().getAnswers(), new Function<AnswerDTO, AnswerDTOWrapper>() {

            @Override
            public AnswerDTOWrapper apply(AnswerDTO from) {
                return new AnswerDTOWrapper(from);
            }
        }));
    }


    private Long getNextQuestionId() {
        try {
            return MaternalWebSession.get().nextQuestionId();
        } catch (EmptyStackException e) {
            return 0l;
        }
    }

    private IModel<QuestionDetailQueryResult> getCurrentQuestion() {
        return new QuestionDetailLoadableDetachableModel(currentQuestionId);
    }

    private void forwardToNextPage(IModel<MaternalCaseDTO> maternalCaseLoadableDetachableModel) {
        if (currentQuestion.getObject().getQuestion().getFeedback() != null && !"".equals(currentQuestion.getObject().getQuestion().getFeedback())) {
            setResponsePage(new FeedbackPage(currentQuestion, maternalCaseLoadableDetachableModel));
            return;
        }

        if (getNextQuestionId() == 0l) {
            setResponsePage(HomePage.class);
            return;
        }

        setResponsePage(new AskQuestionPage(maternalCaseLoadableDetachableModel));
    }


    private class AnswerDTOWrapper implements Serializable {
        private AnswerDTO answer;
        private boolean selected = false;

        public AnswerDTOWrapper(AnswerDTO answer) {

            this.answer = answer;
        }

        public Boolean isSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

        public AnswerDTO getAnswer() {
            return answer;
        }
    }

    private class IsSelectedPredicate implements Predicate<AnswerDTOWrapper> {

        @Override
        public boolean apply(AnswerDTOWrapper input) {
            return input.isSelected();
        }
    }

    private class ExtractIdFunction implements Function<AnswerDTOWrapper, Long> {

        @Override
        public Long apply(AnswerDTOWrapper from) {
            return from.getAnswer().getId();
        }
    }

    private class WrappedAnswersModel implements IModel<List<AnswerDTOWrapper>> {
        @Override
        public List<AnswerDTOWrapper> getObject() {
            return wrappedAnswers;
        }

        @Override
        public void setObject(List<AnswerDTOWrapper> object) {

        }

        @Override
        public void detach() {

        }
    }
}
