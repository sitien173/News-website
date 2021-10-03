package ptit.ltw.Configuration.Ckfinder.authentication;

import com.cksource.ckfinder.authentication.Authenticator;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ptit.ltw.model.Role;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;
import javax.servlet.ServletContext;
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
