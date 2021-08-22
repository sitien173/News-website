package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Entity.User;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Service.UserService;
import ptit.ltw.Service.VerificationTokenService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final VerificationTokenService verificationTokenService;
    private final UserService userService;
    @GetMapping
    public String showViewRegistration(Model model){
        if(!model.containsAttribute("user")){
            model.addAttribute("user", new User());
        }
        return "registration";
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registrationForm(@Valid @ModelAttribute("user") User user,
                                   BindingResult result,
                                   Model model){
        if(result.hasErrors()) return "registration";
        else if(userService.findByEmail(user.getEmail()) != null){
            result.rejectValue("email","error","Email is exist");
            return "registration";
        }else if(userService.findByPhone(user.getPhone()) != null){
            result.rejectValue("phone","error","Phone is exist");
            return "registration";
        }
        // TODO: check email isExist
        userService.save(user);
        return "redirect:/wait-confirm?info=We have sent a confirmation code to your email. Please check your email and confirm your account";
    }
    private void setAuthentication(HttpSession session, VerificationToken verificationToken){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(verificationToken.getUser().getEmail(), null, verificationToken.getUser().getAuthorities());
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);
    }

    @GetMapping(value = "/confirm", params = "token")
    public String confirmToken(@RequestParam("token") String token, HttpSession session) {
       VerificationToken verificationToken = verificationTokenService.confirmToken(token);
       setAuthentication(session,verificationToken);
        return "redirect:/home";
    }
}
