package net.umask.akna.web.components;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 5-dec-2009
 * Time: 21:31:59
 */
public abstract class DataTableLinkPanel<T> extends Panel {
    public DataTableLinkPanel(String componentId, final IModel<T> rowModel, Model<String> labelModel) {
        super(componentId);
        Link l;
        add(l = new Link("link") {

            @Override
            public void onClick() {
                DataTableLinkPanel.this.onClick(rowModel);
            }
        });
        l.add(new Label("label", labelModel));
    }

    protected abstract void onClick(IModel<T> rowModel);


}
