package net.umask.akna.web.pages;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.umask.akna.commands.DeleteCaseCommand;
import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.query.CountAllCasesQuery;
import net.umask.akna.query.GetAllCasesQuery;
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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 29-nov-2009
 * Time: 9:10:24
 */
@AuthorizeInstantiation({"ROLE_ADMIN"})
public class ListCases extends BasePage {


    public ListCases() {
        List<IColumn<MaternalCaseDTO>> columns = Lists.newArrayListWithCapacity(4);
        columns.add(new AbstractColumn<MaternalCaseDTO>(new Model<String>("edit")) {
            @Override
            public void populateItem(Item<ICellPopulator<MaternalCaseDTO>> cellItem, String componentId, final IModel<MaternalCaseDTO> rowModel) {
                cellItem.add(new DataTableLinkPanel<MaternalCaseDTO>(componentId, rowModel, new Model<String>("edit")) {
                    @Override
                    protected void onClick(IModel<MaternalCaseDTO> rowModel) {
                        setResponsePage(new EditMaternalCasePage(rowModel));
                    }
                });


            }
        });

        columns.add(new AbstractColumn<MaternalCaseDTO>(new Model<String>("delete")) {
            @Override
            public void populateItem(Item<ICellPopulator<MaternalCaseDTO>> cellItem, String componentId, final IModel<MaternalCaseDTO> rowModel) {
                cellItem.add((new DataTableLinkPanel<MaternalCaseDTO>(componentId, rowModel, new Model<String>("delete")) {
                    @Override
                    protected void onClick(IModel<MaternalCaseDTO> rowModel) {
                        MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(new DeleteCaseCommand(rowModel.getObject().getId()));
                    }
                }));

            }
        });

        columns.add(new AbstractColumn<MaternalCaseDTO>(new Model<String>("edit questions")) {
            @Override
            public void populateItem(Item<ICellPopulator<MaternalCaseDTO>> cellItem, String componentId, IModel<MaternalCaseDTO> rowModel) {
                cellItem.add((new DataTableLinkPanel<MaternalCaseDTO>(componentId, rowModel, new Model<String>("edit questions")) {

                    @Override
                    protected void onClick(IModel<MaternalCaseDTO> rowModel) {
                        setResponsePage(ListQuestionsPage.class, new PageParameters(ImmutableMap.of(Constants.PARENT_CASE_ID, rowModel.getObject().getId())));
                    }
                }));

            }
        });

        columns.add(new PropertyColumn<MaternalCaseDTO>(new Model<String>("title"), "title") {
            @Override
            public String getCssClass() {
                return "titlecol";
            }
        });
        add(new DefaultDataTable<MaternalCaseDTO>("cases", columns, new MaternalCaseDataProvider(), 8));
        add(new Link("addCase") {

            @Override
            public void onClick() {
                setResponsePage(new EditMaternalCasePage(new MaternalCaseLoadableDetachableModel()));
            }
        });

    }


    private class MaternalCaseDataProvider extends SortableDataProvider<MaternalCaseDTO> {
        @Override
        public Iterator<? extends MaternalCaseDTO> iterator(int first, int count) {
            return MaternalWicketApplication.get().getQueryService().executeQuery(new GetAllCasesQuery(first, count)).iterator();
        }

        @Override
        public int size() {
            return MaternalWicketApplication.get().getQueryService().executeQuery(new CountAllCasesQuery());
        }

        @Override
        public IModel<MaternalCaseDTO> model(MaternalCaseDTO object) {
            return new MaternalCaseLoadableDetachableModel(object);
        }


    }
}
