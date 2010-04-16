package net.umask.akna.web.pages;

import net.umask.akna.web.components.LoginPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 15-nov-2009
 * Time: 21:41:12
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends BasePage {

    public LoginPage() {
        add(new LoginPanel("loginPanel"));
    }
}
