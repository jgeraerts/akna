package net.umask.akna.web.pages;

import net.umask.akna.web.MaternalWebSession;
import net.umask.akna.web.components.LoginPanel;
import net.umask.akna.web.components.NavigationLinkContainer;
import net.umask.akna.web.components.UserMenuPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 15-nov-2009
 * Time: 18:10:39
 */
public class BasePage extends WebPage {
    public BasePage() {
        add(new NavigationLinkContainer("home", new HomeLink("link")));
        add(new NavigationLinkContainer("contact", new ContactLink("link")));
        add(new NavigationLinkContainer("help", new HelpLink("link")));

        add(new FeedbackPanel("feedback"));
        add(new LoginPanel("loginPanel") {
            @Override
            public boolean isVisible() {
                return !(getPage() instanceof LoginPage || MaternalWebSession.get().isSignedIn());
            }
        });
        add(new UserMenuPanel("userMenuPanel") {
            @Override
            public boolean isVisible() {
                return MaternalWebSession.get().isSignedIn();
            }
        });

    }


    private class HomeLink extends BookmarkablePageLink<HomePage> {
        public HomeLink(String id) {
            super(id, HomePage.class);
        }
    }

    private class ContactLink extends BookmarkablePageLink<ContactPage> {
        public ContactLink(String id) {
            super(id, ContactPage.class);
        }
    }

    private class HelpLink extends BookmarkablePageLink<HelpPage> {
        public HelpLink(String id) {
            super(id, HelpPage.class);
        }
    }
}
