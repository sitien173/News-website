package ptit.ltw.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminHomeController {
    @GetMapping
    public String showViewDashBoard(){
        return "admin/index";
    }
}
