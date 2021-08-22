package ptit.ltw.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {
    @GetMapping
    public String showViewLoginAdmin(@RequestParam(value = "messenger",required = false)String messenger,
                                     Model model){
        if(messenger != null) model.addAttribute("info", "Login error. Please input again!");
        return "admin/login";
    }
}
