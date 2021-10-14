package ptit.ltw.Configuration.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final String processUrl;

    public CaptchaAuthenticationFilter(String defaultFilterProcessesUrl, String failureUrl) {
        super(defaultFilterProcessesUrl);
        this.processUrl = defaultFilterProcessesUrl;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse)response;
        if(processUrl.equals(req.getServletPath()) && "POST".equalsIgnoreCase(req.getMethod())){
            String expect = req.getSession().getAttribute("captcha-security").toString();
            //remove from session
            req.getSession().removeAttribute("captcha-security");

            if (expect != null && !expect.equals(req.getParameter("captcha"))){
                log.info("Verify Code Captcha Filter: Invalid Captcha");
                unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Wrong captcha code."));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
