package net.umask.akna.web.pages;

import net.umask.akna.dto.MaternalCaseDTO;
import net.umask.akna.query.GetAllCasesQuery;
import net.umask.akna.web.MaternalWicketApplication;
import net.umask.akna.web.MaternalWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * User: Me
 * Date: 31-jul-2010
 * Time: 15:02:22
 */
public class ChooseCasePage extends BasePage {

    public ChooseCasePage() {
        add(new ListView<MaternalCaseDTO>("links", MaternalWicketApplication.get().getQueryService().executeQuery(new GetAllCasesQuery())) {

            @Override
            protected void populateItem(final ListItem<MaternalCaseDTO> listItem) {
                listItem.add(new Link("link") {

                    @Override
                    public void onClick() {

                        setResponsePage(new DisplayCasePage(listItem.getModelObject().getId()));

                    }
                }.add(new Label("label", listItem.getModelObject().getTitle())));
            }
        });
    }
}
