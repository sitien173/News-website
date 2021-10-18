package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Service.IService.UserService;

@Controller
@RequestMapping("/user/update-password")
@AllArgsConstructor
public class UpdatePasswordController {
    private final UserService userService;
    @GetMapping
    public String showViewUpdatePassword(){
        return "user/update-password";
    }

    @PostMapping
    public String handlerFormUpdate(@RequestParam("password") String password,
                                    @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext){
        String email = securityContext.getAuthentication().getName();
        userService.updatePassword(email,password);
        return "user/index";
    }
}
