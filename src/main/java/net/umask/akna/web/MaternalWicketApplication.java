package net.umask.akna.web;


import net.umask.akna.service.CommandDispatcher;
import net.umask.akna.service.QueryService;
import net.umask.akna.web.pages.HomePage;
import net.umask.akna.web.pages.ListQuestionsPage;
import net.umask.akna.web.pages.LoginPage;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.io.WicketObjectStreamFactory;
import org.apache.wicket.util.lang.Objects;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 15-nov-2009
 * Time: 18:09:22
 */
public class MaternalWicketApplication extends AuthenticatedWebApplication {

    @SpringBean
    private QueryService queryService;
    @SpringBean
    private CommandDispatcher commandDispatcher;
    @SpringBean
    private AuthenticationManager authenticationManager;


    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new SpringComponentInjector(this));

        InjectorHolder.getInjector().inject(this);

        mountBookmarkablePage("login", LoginPage.class);
        mountBookmarkablePage("admin/questions", ListQuestionsPage.class);
        Objects.setObjectStreamFactory(new WicketObjectStreamFactory());
        //getMarkupSettings().setStripWicketTags(true);
    }

    @Override
    protected Class<MaternalWebSession> getWebSessionClass() {
        return MaternalWebSession.class;
    }

    @Override
    protected Class<HomePage> getSignInPageClass() {
        return HomePage.class;
    }

    public static MaternalWicketApplication get() {
        return (MaternalWicketApplication) AuthenticatedWebApplication.get();
    }

    public QueryService getQueryService() {
        return queryService;
    }


    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }
}
