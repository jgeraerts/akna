package net.umask.akna.web.components;

import net.umask.akna.web.components.SanitizingStringModel;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.PropertyModel;
import org.owasp.validator.html.Policy;

/**
 * Created by IntelliJ IDEA.
 * User: JoGeraerts
 * Date: 24-aug-2010
 */
public class HtmlSafeMultiLineLabel extends MultiLineLabel {
   public HtmlSafeMultiLineLabel(String id, PropertyModel<String> stringPropertyModel) {
        super(id,new SanitizingStringModel(stringPropertyModel));
    }
}
