package net.umask.akna.web.pages;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.umask.akna.commands.DeleteOrderedCaseQuestionAssociationCommand;
import net.umask.akna.dto.QuestionDTO;
import net.umask.akna.query.QuestionForCaseCountQuery;
import net.umask.akna.query.QuestionsForCaseQuery;
import net.umask.akna.web.Constants;
import net.umask.akna.web.MaternalWicketApplication;
import net.umask.akna.web.components.DataTableLinkPanel;
import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 25-nov-2009
 * Time: 20:35:37
 */
@AuthorizeInstantiation({"ROLE_ADMIN"})
public class ListQuestionsPage extends BasePage {


    public ListQuestionsPage(final PageParameters params) {
        add(new BookmarkablePageLink<ListCases>("backLink", ListCases.class));

        List<IColumn<QuestionDTO>> columns = Lists.newArrayListWithCapacity(4);
        columns.add(new AbstractColumn<QuestionDTO>(new Model<String>("edit")) {
            @Override
            public void populateItem(Item<ICellPopulator<QuestionDTO>> cellItem, String componentId, final IModel<QuestionDTO> rowModel) {
                cellItem.add(new DataTableLinkPanel<QuestionDTO>(componentId, rowModel, new Model<String>("edit")) {
                    @Override
                    protected void onClick(IModel<QuestionDTO> rowModel) {
                        setResponsePage(QuestionDetailPage.class, new PageParameters(ImmutableMap.of(Constants.QUESTION_ID, rowModel.getObject().getId())));
                    }
                });


            }
        });

        columns.add(new AbstractColumn<QuestionDTO>(new Model<String>("delete")) {
            @Override
            public void populateItem(Item<ICellPopulator<QuestionDTO>> cellItem, String componentId, final IModel<QuestionDTO> rowModel) {
                cellItem.add((new DataTableLinkPanel<QuestionDTO>(componentId, rowModel, new Model<String>("delete")) {
                    @Override
                    protected void onClick(IModel<QuestionDTO> rowModel) {
                        MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new DeleteOrderedCaseQuestionAssociationCommand(rowModel.getObject().getAssociationId()));
                    }
                }));

            }
        });

        columns.add(new PropertyColumn<QuestionDTO>(new Model<String>("question"), "question"));

        add(new DefaultDataTable<QuestionDTO>("questions", columns, new QuestionForCaseProvider(params.getLong(Constants.PARENT_CASE_ID)), 8));

        add(new BookmarkablePageLink<AddQuestionPage>("addQuestionLink", AddQuestionPage.class, new PageParameters(ImmutableMap.copyOf(params))));


    }


    private class QuestionForCaseProvider extends SortableDataProvider<QuestionDTO> {
        private long parentCaseId;

        public QuestionForCaseProvider(long aLong) {
            parentCaseId = aLong;
        }

        @Override
        public Iterator<? extends QuestionDTO> iterator(int first, int count) {
            return MaternalWicketApplication.get().getQueryService().executeQuery(new QuestionsForCaseQuery(parentCaseId, first, count)).iterator();
        }

        @Override
        public int size() {
            return MaternalWicketApplication.get().getQueryService().executeQuery(new QuestionForCaseCountQuery(parentCaseId));
        }

        @Override
        public IModel<QuestionDTO> model(QuestionDTO object) {
            return new Model<QuestionDTO>(object);
        }
    }
}
