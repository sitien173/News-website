package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.VerificationToken;
import ptit.ltw.Service.IService.FileStoreService;
import ptit.ltw.Service.IService.UserService;
import ptit.ltw.Service.IService.VerificationTokenService;

import javax.validation.Valid;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    private final FileStoreService fileStoreService;
    private final VerificationTokenService verificationTokenService;
    private final UserService userService;
    
    @GetMapping
    public String showViewRegistration(Model model){
        if(!model.containsAttribute("appUser")){
            model.addAttribute("appUser", new AppUser());
        }
        return "user/registration";
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registrationForm(@Valid @ModelAttribute("appUser") AppUser appUser,
                                   BindingResult result,
                                   @RequestParam("file")MultipartFile file) throws FileNotFoundException {
        if(result.hasErrors()) return "user/registration";
        else if(userService.findByEmail(appUser.getEmail()) != null){
            result.rejectValue("email","error","Email is exist");
            return "user/registration";
        }
        if(!file.isEmpty())  appUser.setAvatar(fileStoreService.upload(file));
        // TODO: check email isExist
        userService.save(appUser);
        return "redirect:/wait-confirm?info=We have sent a confirmation code to your email. Please check your email and confirm your account";
    }



    @GetMapping(value = "/confirm", params = "token")
    public String confirmToken(@RequestParam("token") String token) {
       VerificationToken verificationToken = verificationTokenService.confirmToken(token);
       userService.setAuthentication(verificationToken.getAppUser());
       return "redirect:/home";
    }
}
