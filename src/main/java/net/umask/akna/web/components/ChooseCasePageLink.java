package net.umask.akna.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import net.umask.akna.web.pages.ChooseCasePage;

/**
 * User: Me
 * Date: 31-jul-2010
 * Time: 15:01:22
 */
public class ChooseCasePageLink extends BookmarkablePageLink<ChooseCasePage> {
    public ChooseCasePageLink(String s) {
        super(s,ChooseCasePage.class);
    }
}
