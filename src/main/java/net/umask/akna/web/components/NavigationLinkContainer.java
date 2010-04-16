package net.umask.akna.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class NavigationLinkContainer extends WebMarkupContainer {

    public NavigationLinkContainer(String id, final BookmarkablePageLink<?> link) {
        super(id);
        add(link);
        add(new SimpleAttributeModifier("class", "active") {

            @Override
            public boolean isEnabled(Component component) {
                return getPage().getClass().equals(link.getPageClass());
            }
        });

    }
}
