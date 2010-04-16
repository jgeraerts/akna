package net.umask.akna.web.pages;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.umask.akna.commands.UpdateOrderCommand;
import net.umask.akna.dto.OrderedQuestionDTO;
import net.umask.akna.query.OrderedQuestionsQuery;
import net.umask.akna.web.MaternalWicketApplication;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.wicketstuff.scriptaculous.dragdrop.SortableListView;

/**
 * User: Me
 * Date: 6-jan-2010
 * Time: 21:31:41
 */
@AuthorizeInstantiation({"ROLE_ADMIN"})
public class SortQuestions extends BasePage {

    public SortQuestions() {

        add(new SortableListView<OrderedQuestionDTO>("list", "row", MaternalWicketApplication.get().getQueryService().executeQuery(new OrderedQuestionsQuery())) {

            @Override
            protected void populateItemInternal(ListItem<OrderedQuestionDTO> orderedQuestionDTOListItem) {
                orderedQuestionDTOListItem.add(new Label("case", orderedQuestionDTOListItem.getModelObject().getMaternalCase()));
                orderedQuestionDTOListItem.add(new Label("question", orderedQuestionDTOListItem.getModelObject().getQuestion()));
            }

            @Override
            protected void onModelChanged() {
                MaternalWicketApplication.get().getCommandDispatcher().dispatchCommand(
                        new UpdateOrderCommand(
                                ImmutableList.copyOf(
                                        Iterables.transform(getModel().getObject(), new Function<OrderedQuestionDTO, Long>() {

                                            @Override
                                            public Long apply(OrderedQuestionDTO from) {
                                                return from.getId();
                                            }
                                        })
                                )

                        ));
            }
        });

    }
}
