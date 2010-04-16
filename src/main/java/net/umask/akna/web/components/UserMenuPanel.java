package net.umask.akna.web.components;

import net.umask.akna.web.MaternalWebSession;
import net.umask.akna.web.pages.*;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * User: Me
 * Date: 7-feb-2010
 * Time: 21:54:52
 */
public class UserMenuPanel extends Panel {
    public UserMenuPanel(String id) {
        super(id);
        add(new AccountLink("accountLink").add(new Label("username", MaternalWebSession.get().getUsername())));

        add(new Link("start") {
            @Override
            public void onClick() {
                MaternalWebSession.get().initializeQuestionStack();
                setResponsePage(AskQuestionPage.class);
            }
        });
        add(new NavigationLinkContainer("accountLink2", new AccountLink(("link"))));
        add(new NavigationLinkContainer("changePasswordLink", new ChangePasswordLink(("link"))));
        add(new NavigationLinkContainer("listCasesLink", new ListCasesLink("link")));
        add(new NavigationLinkContainer("sortQuestionsLink", new SortQuestionsLink("link")));
        add(new Link("logout") {

            @Override
            public void onClick() {
                MaternalWebSession.get().signOut();
                setResponsePage(HomePage.class);
            }
        });
    }


    @AuthorizeAction(action = Action.RENDER, roles = {"ROLE_ADMIN"})
    private static class ListCasesLink extends BookmarkablePageLink<ListCases> {
        public ListCasesLink(String id) {
            super(id, ListCases.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {"ROLE_ADMIN"})
    private static class SortQuestionsLink extends BookmarkablePageLink<SortQuestions> {
        public SortQuestionsLink(String id) {
            super(id, SortQuestions.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {"ROLE_ADMIN"})
    private class AccountLink extends BookmarkablePageLink<AccountPage> {
        public AccountLink(String id) {
            super(id, AccountPage.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {"ROLE_ADMIN"})
    private class ChangePasswordLink extends BookmarkablePageLink<ChangePasswordPage> {
        public ChangePasswordLink(String id) {
            super(id, ChangePasswordPage.class);
        }
    }
}