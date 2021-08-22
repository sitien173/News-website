package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Service.UserService;
import ptit.ltw.Service.VerificationTokenService;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/forgot-password")
public class ForgotPasswordController {
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    @GetMapping
    public String showView() {
        return "forgot-password";
    }

    @PostMapping
    public String sendCode(@RequestParam("email") String email, Model model) {
        model.addAttribute("info", "We have send code to mail. Please check mail and change password");
        userService.forgotPassword(email);
        return "wait-confirm";
    }
    private void setAuthentication(HttpSession session, VerificationToken verificationToken){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(verificationToken.getUser().getEmail(), null, verificationToken.getUser().getAuthorities());
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);
    }
    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token,HttpSession session) {
        VerificationToken verificationToken = verificationTokenService.confirmToken(token);
        setAuthentication(session, verificationToken);
        return "redirect:/update-password";
    }
}
