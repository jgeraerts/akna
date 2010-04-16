package net.umask.akna.web.components;

import net.umask.akna.web.MaternalWebSession;
import org.apache.wicket.markup.html.link.Link;

/**
 * User: Me
 * Date: 13-jan-2010
 * Time: 11:56:03
 */
public class SignOutLink extends Link {
    public SignOutLink(String id) {
        super(id);
    }

    @Override
    public void onClick() {
        MaternalWebSession.get().signOut();
    }

    @Override
    public boolean isVisible() {
        return MaternalWebSession.get().isSignedIn();
    }
}
