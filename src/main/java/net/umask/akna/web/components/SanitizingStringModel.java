package net.umask.akna.web.components;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.owasp.validator.html.*;

/**
 * Created by IntelliJ IDEA.
 * User: JoGeraerts
 * Date: 24-aug-2010
 * Time: 22:54:19
 */
public class SanitizingStringModel implements IModel<String> {
    private IModel<String> stringPropertyModel;
    private static Policy policy;

    public SanitizingStringModel(IModel<String> stringPropertyModel) {
        this.stringPropertyModel = stringPropertyModel;
    }

    @Override
    public String getObject() {
        AntiSamy as = new AntiSamy();
        String unsafe = stringPropertyModel.getObject();
        if(unsafe == null) {
            return null;
        }
        try {
            CleanResults cr = as.scan(unsafe,getPolicy());
            return cr.getCleanHTML();
        } catch (ScanException e) {
            throw new RuntimeException(e);
        } catch (PolicyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setObject(String object) {
        stringPropertyModel.setObject(object);
    }

    @Override
    public void detach() {
        stringPropertyModel.detach();
    }

    public Policy getPolicy() {
        if(policy == null){
            try {
                policy  = Policy.getInstance(HtmlSafeMultiLineLabel.class.getClassLoader().getResourceAsStream("antisamy-akna.xml"));
            } catch (PolicyException e) {
                throw new RuntimeException(e);
            }
        }
        return policy;
    }
}
