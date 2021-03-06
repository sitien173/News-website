package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Service.IService.UserService;
import ptit.ltw.Service.IService.VerificationTokenService;

@Controller
@AllArgsConstructor
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    @GetMapping
    public String showView() {
        return "user/forgot-password";
    }

    @PostMapping
    public String sendCode(@RequestParam("email") String email, Model model) {
        model.addAttribute("info", "We have send code to mail. Please check mail and change password");
        userService.forgotPassword(email);
        return "user/wait-confirm";
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.confirmToken(token);
        userService.setAuthentication(verificationToken.getAppUser());
        return "user/update-password";
    }
}
