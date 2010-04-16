package net.umask.akna.web.components;

import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 15-nov-2009
 * Time: 21:42:11
 */
public class LoginPanel extends Panel {
    @SuppressWarnings({"UnusedDeclaration"})
    private String username;
    @SuppressWarnings({"UnusedDeclaration"})
    private String password;


    public LoginPanel(String id) {
        super(id);
        Form form = new Form<LoginPanel>("loginForm", new CompoundPropertyModel<LoginPanel>(this)) {
            @Override
            protected void onSubmit() {

                AuthenticatedWebSession session = AuthenticatedWebSession.get();
                if (session.signIn(username, password)) {
                    setDefaultResponsePageIfNecessary();
                } else {
                    error(getString("login.failed"));
                }

                super.onSubmit();
            }

            private void setDefaultResponsePageIfNecessary() {
                if (!continueToOriginalDestination()) {
                    setResponsePage(getApplication().getHomePage());
                }
            }

        };
        form.add(new RequiredTextField("username"));
        form.add(new PasswordTextField("password"));
        add(form);
    }
}
