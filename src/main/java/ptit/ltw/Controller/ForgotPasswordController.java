package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        return "forgot-password";
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token) {
        verificationTokenService.confirmToken(token);
        VerificationToken verificationToken = verificationTokenService.findByToken(token).orElseThrow(() -> new IllegalStateException("Token is not exist"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(verificationToken.getUser().getEmail(), null, verificationToken.getUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "update-password";
    }
}
