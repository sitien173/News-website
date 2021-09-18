package ptit.ltw.Controller.Public;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wait-confirm")
public class WaitConfirmController {
    @GetMapping
    public String showView(@RequestParam(value = "info",required = false) String info, Model model){
        if(info != null) model.addAttribute("info",info);
        return "public/wait-confirm";
    }
}
