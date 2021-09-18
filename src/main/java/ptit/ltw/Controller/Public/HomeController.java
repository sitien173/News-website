package ptit.ltw.Controller.Public;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"","/index","/home"})
public class HomeController {
    @GetMapping
    public String showViewHome(){
        return "public/index";
    }

}
