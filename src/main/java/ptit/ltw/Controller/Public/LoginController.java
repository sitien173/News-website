package ptit.ltw.Controller.Public;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String showViewLogin(@RequestParam(value = "info",required = false) String messenger,
                                Model model){
        if(messenger != null) model.addAttribute("info",messenger);
        return "public/login";
    }
}
