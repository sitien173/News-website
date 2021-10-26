package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Service.IService.UserService;

@Controller
@RequestMapping("/user/update-password")
@AllArgsConstructor
public class UpdatePasswordController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    @PostMapping
    public String handlerFormUpdate(@RequestParam("password") String password,
                                    @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext){
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setIsEnable(false);
        userService.save(appUser);
        return "redirect:/wait-confirm?info=We have sent a confirmation code to your email. Please check your email and authentication your account";
    }
}
