package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Entity.User;
import ptit.ltw.Service.UserService;
import ptit.ltw.Service.VerificationTokenService;

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
        model.addAttribute("info", "We have sent a confirmation code to your email. Please check your email and confirm your account");
        return "wait-confirm";
    }
    @GetMapping(value = "/confirm", params = "token")
    public String confirmToken(@RequestParam("token") String token) {
        verificationTokenService.confirmToken(token);
        return "redirect:/home";
    }
}
