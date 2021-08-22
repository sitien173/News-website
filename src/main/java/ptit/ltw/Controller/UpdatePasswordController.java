package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Service.UserService;

@Controller
@RequestMapping("/update-password")
@AllArgsConstructor
public class UpdatePasswordController {
    private final UserService userService;
    @GetMapping
    public String showViewUpdatePassword(){
        return "update-password";
    }

    @PostMapping
    public String handlerFormUpdate(@RequestParam("password") String password,
                                    @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext){
        String email = securityContext.getAuthentication().getName();
        userService.updatePassword(email,password);
        return "index";
    }
}
