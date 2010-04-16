package net.umask.akna.web.components;


import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.util.collections.MiniMap;
import org.apache.wicket.util.template.PackagedTextTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TinyMceEnabler extends AbstractBehavior {

    private List<Component> components = new ArrayList<Component>(1);

    @Override
    public void bind(Component component) {
        if (!(component instanceof TextArea)) {
            throw new IllegalStateException(getClass().getName() + " can only be added to " +
                    TextArea.class.getName());
        }
        components.add(component);
        component.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {

        response.renderJavascriptReference(new ResourceReference(TinyMceEnabler.class,
                "tiny_mce/tiny_mce.js"));

        StringBuilder idlist = new StringBuilder();
        Iterator<Component> it = components.iterator();
        while (it.hasNext()) {
            idlist.append("\"").append(it.next().getMarkupId()).append("\"");
            if (it.hasNext()) {
                idlist.append(",");
            }
        }

        MiniMap vars = new MiniMap(1);
        vars.put("idlist", idlist.toString());

        PackagedTextTemplate enabler = new PackagedTextTemplate(TinyMceEnabler.class, "enabler.js");

        response.renderJavascript(enabler.asString(vars), getClass().getName() +
                System.identityHashCode(this));

    }

}
