package ptit.ltw.Controller.Public;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptit.ltw.Utils.CaptchaUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/captcha")
@AllArgsConstructor
public class CaptchaController {
    @GetMapping("/generator")
    public void generatorCaptcha(HttpServletRequest request, HttpServletResponse response){
        Captcha captcha = CaptchaUtil.createCaptcha(300,50);
        HttpSession session = request.getSession();
        session.setAttribute("captcha-security",captcha.getAnswer());
        //Show it on the web page
        CaptchaServletUtil.writeImage(response,captcha.getImage());
    }
}
