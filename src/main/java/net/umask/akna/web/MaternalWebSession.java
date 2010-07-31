package net.umask.akna.web;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import static com.google.common.collect.Iterables.transform;
import net.umask.akna.query.GetIdsForMainQuestionsInReverseOrderQuery;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 15-nov-2009
 */
public class MaternalWebSession extends AuthenticatedWebSession {
    Stack<Long> questionIdStack = new Stack<Long>();
    private Roles roles = new Roles();

    public String getUsername() {
        return username;
    }

    private String username;

    public MaternalWebSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(String username, String password) {
        try {

            AuthenticationManager mgr = MaternalWicketApplication.get().getAuthenticationManager();
            Authentication authentication = mgr.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String[] roles = Iterables
                    .toArray(transform(
                            authentication.getAuthorities(), new GrantedAuthorityToStringFunction()
                    ), String.class);
            this.roles = new Roles(roles);
            this.username = username;
            return authentication.isAuthenticated();
        } catch (AuthenticationException e) {
            return false;
        }
    }

    @Override
    public Roles getRoles() {
        return roles;
    }

    public Long nextQuestionId() {
        return questionIdStack.peek();
    }

    public void popOffCurrentQuestion() {
        questionIdStack.pop();
    }

    public static MaternalWebSession get() {
        return (MaternalWebSession) AuthenticatedWebSession.get();
    }

    public void initializeQuestionStack(Long caseId) {
        questionIdStack.addAll(MaternalWicketApplication.get().getQueryService().executeQuery(new GetIdsForMainQuestionsInReverseOrderQuery(caseId)));
    }

    public void addNewQuestionIds(List<Long> selectedAnswerIds) {
        questionIdStack.addAll(selectedAnswerIds);
    }

    @Override
    public void signOut() {
        super.signOut();
        roles = new Roles();
    }

    private static class GrantedAuthorityToStringFunction implements Function<GrantedAuthority, String> {

        @Override
        public String apply(GrantedAuthority from) {
            return from.getAuthority();
        }
    }
}
