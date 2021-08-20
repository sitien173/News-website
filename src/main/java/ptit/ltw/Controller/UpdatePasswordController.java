package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String handlerFormUpdate(@RequestParam("password") String password){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updatePassword(email,password);
        return "index";
    }
}
