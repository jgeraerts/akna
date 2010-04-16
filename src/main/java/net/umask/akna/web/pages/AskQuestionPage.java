package net.umask.akna.web.pages;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import net.umask.akna.dto.AnswerDTO;
import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.query.GetMaternalCaseDTOByIdQuery;
import net.umask.akna.query.GetQuestionIdsForSelectedAnswersQuery;
import net.umask.akna.query.QuestionDetailQuery;
import net.umask.akna.query.QuestionDetailQueryResult;
import net.umask.akna.web.MaternalWebSession;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.List;


/**
 * User: Me
 * Date: 7-jan-2010
 * Time: 20:20:39
 */
public class AskQuestionPage extends BasePage {
    private Long currentQuestionId;
    private QuestionDetailQueryResult currentQuestion;
    private MaternalCaseDTO currentCase;
    private List<AnswerDTOWrapper> wrappedAnswers;

    public AskQuestionPage() {
        currentQuestionId = getNextQuestionId();
        currentQuestion = getCurrentQuestion();
        currentCase = getCurrentCase();
        wrappedAnswers = getWrappedAnswers();
        add(new MultiLineLabel("case", new Model<String>() {
            @Override
            public String getObject() {
                return currentCase != null ? currentCase.getBody() : "";
            }
        }
        ).setEscapeModelStrings(false));
        add(new MultiLineLabel("question", new Model<String>() {

            @Override
            public String getObject() {
                return currentQuestion.getQuestion().getQuestion();
            }
        }
        ));

        Form form;
        add(form = new Form("answerForm") {
            @Override
            protected void onSubmit() {
                List<Long> selectedAnswerIds =
                        ImmutableList.copyOf(
                                transform(
                                        filter(
                                                wrappedAnswers,
                                                new IsSelectedPredicate()),
                                        new ExtractIdFunction()));
                MaternalWebSession.get().popOffCurrentQuestion();
                MaternalWebSession.get().addNewQuestionIds(MaternalWicketApplication.get().getQueryService().executeQuery(new GetQuestionIdsForSelectedAnswersQuery(selectedAnswerIds)));
                setResponsePage(new FeedbackPage(currentQuestionId, selectedAnswerIds));

            }
        });


        ListView<AnswerDTOWrapper> listView;
        form.add(listView = new ListView<AnswerDTOWrapper>("list", new WrappedAnswersModel()) {

            @Override
            protected void populateItem(ListItem<AnswerDTOWrapper> answerDTOListItem) {

                answerDTOListItem.add(new CheckBox("check", new PropertyModel<Boolean>(answerDTOListItem.getModel(), "selected")));
                answerDTOListItem.add(new Label("answer", new PropertyModel<Boolean>(answerDTOListItem.getModel(), "answer.answer")));

            }
        });
        listView.setReuseItems(true);
    }

    private ImmutableList<AnswerDTOWrapper> getWrappedAnswers() {
        return ImmutableList.copyOf(transform(currentQuestion.getAnswers(), new Function<AnswerDTO, AnswerDTOWrapper>() {

            @Override
            public AnswerDTOWrapper apply(AnswerDTO from) {
                return new AnswerDTOWrapper(from);
            }
        }));
    }

    private MaternalCaseDTO getCurrentCase() {
        return MaternalWicketApplication.get().getQueryService().executeQuery(new GetMaternalCaseDTOByIdQuery(currentQuestion.getQuestion().getMaternalCaseId()));
    }

    private Long getNextQuestionId() {
        try {
            return MaternalWebSession.get().nextQuestionId();
        } catch (EmptyStackException e) {
            return 0l;
        }
    }

    private QuestionDetailQueryResult getCurrentQuestion() {
        return MaternalWicketApplication.get().getQueryService().executeQuery(new QuestionDetailQuery(currentQuestionId));
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
