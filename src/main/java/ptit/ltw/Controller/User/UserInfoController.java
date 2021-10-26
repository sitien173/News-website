package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Service.IService.FileStoreService;
import ptit.ltw.Service.IService.UserService;

import javax.validation.Valid;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/user/info")
@AllArgsConstructor
public class UserInfoController {
    private final UserService userService;
    private final FileStoreService fileStoreService;
    @GetMapping
    public String viewInfo(@SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext,
                           Model model){
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        model.addAttribute("user",appUser);
        return "user/info";
    }

    @GetMapping("/update")
    public String showViewUpdate(@SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext,
                                 Model model){
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        model.addAttribute("user",appUser);
        return "user/user-edit";
    }
    @PostMapping("/update")
    public String handlerUpdate(@Valid @ModelAttribute("user") AppUser appUser,
                                BindingResult result,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("email1") String email) throws FileNotFoundException {
        if(result.hasErrors())  return "user/user-edit";
        if(!appUser.getEmail().equalsIgnoreCase(email)){
            if(userService.findByEmail(email) != null){
                result.rejectValue("email","error","email "+email+" is exist");
                return "user/user-edit";
            }else {
                appUser.setEmail(email);
                // xác thực lại email
                appUser.setIsEnable(false);
            }
        }
        if(!file.isEmpty()) appUser.setAvatar(fileStoreService.upload(file));
        userService.save(appUser);
        return "redirect:/wait-confirm?info=We have sent a confirmation code to your email. Please check your email and authentication your account";

    }

}
