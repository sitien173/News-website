package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.ltw.Dto.VerificationTokenDto;
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

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token,HttpSession session) {
        VerificationTokenDto verificationTokenDto = verificationTokenService.confirmToken(token);
        userService.setAuthentication(session,verificationTokenDto.getAppUserId());
        return "redirect:/update-password";
    }
}
