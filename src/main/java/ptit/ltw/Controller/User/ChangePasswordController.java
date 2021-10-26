package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Entity.AppUser;

@Controller
@RequestMapping("/user/change-password")
@AllArgsConstructor
public class ChangePasswordController {
    private final BCryptPasswordEncoder passwordEncoder;
    @GetMapping
    public String showView(){
        return "user/change-password";
    }
    @PostMapping
    public String handlerChangePassword(@RequestParam("password") String password,
                                        Model model,
                                        @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext){
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        if(!passwordEncoder.matches(password,appUser.getPassword())){
            model.addAttribute("message","Mật khẩu không khớp");
            return "user/change-password";
        }
        return "user/update-password";
    }
}
