package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.ltw.Entity.Subscriber;
import ptit.ltw.Service.IService.SubscriberService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/subscriber")
@AllArgsConstructor
public class SubscriberController {
    private final Environment env;
    private final SubscriberService subscriberService;
    @PostMapping("/add")
    public String handlerAddSupscriber(@RequestParam("email") String email,
                                       HttpServletRequest request){
        String referer = request.getHeader(HttpHeaders.REFERER).replaceAll(env.getRequiredProperty("base.url"),"");
        Subscriber subscriber = new Subscriber(email);
        subscriberService.save(subscriber);
        return "redirect:" + referer + "?message=Damg ky thanh cong";
    }
}
