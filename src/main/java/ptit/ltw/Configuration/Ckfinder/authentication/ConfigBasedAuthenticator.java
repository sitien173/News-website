package ptit.ltw.Configuration.Ckfinder.authentication;

import com.cksource.ckfinder.authentication.Authenticator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import ptit.ltw.Model.Role;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
@Named
public class ConfigBasedAuthenticator implements Authenticator {
    @Inject
    private HttpSession session;
    @Override
    public boolean authenticate() {
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
    }
}
