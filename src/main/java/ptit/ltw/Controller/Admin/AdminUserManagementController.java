package ptit.ltw.Controller.Admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Service.IService.FileStoreService;
import ptit.ltw.Service.IService.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/user-management")
@AllArgsConstructor
public class AdminUserManagementController {
    private final UserService userService;
    private final FileStoreService fileStoreService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static List<AppUser> users;


    private AppUser getAppUser(){
        AppUser appUser = new AppUser();
        appUser.setIsEnable(true);
        return new AppUser();
    }
    private List<AppUser> getUsers(){
        users = userService.getAll();
        return users;
    }

    @ModelAttribute("users")
    public List<AppUser> getCategories(@RequestParam(value = "refresh",required = false) Boolean isRefresh){
        if(isRefresh != null) users = null;
        return users == null ? getUsers() : users;
    }

    @GetMapping
    public String showView(){
        return "admin/user-management";
    }

    @GetMapping("/add")
    public String showViewAdd(Model model){
        model.addAttribute("appUser", getAppUser());
        return "admin/user-management";
    }

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registrationForm(@Valid @ModelAttribute("appUser") AppUser appUser,
                                   BindingResult result,
                                   @RequestParam("file")MultipartFile file) throws IOException {
        if(result.hasErrors()) return "admin/user-management";
        if(userService.findByEmail(appUser.getEmail()) != null){
            result.rejectValue("email","error","Email is exist");
            return "admin/user-management";
        }
        // upload file to uploads
        if(!file.isEmpty())  appUser.setAvatar(fileStoreService.upload(file));
        String passwordEncode = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(passwordEncode);
        userService.save(appUser);
        return "redirect:/admin/user-management?refresh=true";
    }


    @GetMapping("/{id}")
    public String getInfo(@PathVariable("id") int id,Model model){
        model.addAttribute("userEdit",userService.findById((long) id));
        return "admin/user-edit";
    }

    @PostMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateForm(@Valid @ModelAttribute("userEdit") AppUser appUser,
                                   BindingResult result,
                                   @RequestParam("email1") String email,
                                   @RequestParam("file")MultipartFile file) throws IOException {
        if(result.hasErrors()) return "admin/user-edit";
        // check change email
        else if (!appUser.getEmail().equals(email)){
            if(userService.findByEmail(email) != null){
                result.rejectValue("email","error","Email is exist");
                return "admin/user-edit";
            }
            appUser.setEmail(email);
        } else if( !file.isEmpty() )
            appUser.setAvatar(fileStoreService.upload(file));
        // upload file to uploads
        // TODO: check email isExist
        userService.save(appUser);
        return "redirect:/admin/user-management?refresh=true";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("userId") int[] ids){
        for (int id : ids)  userService.delete((long) id);
        return "redirect:/admin/user-management?refresh=true";
    }

    @PostMapping("/change-password")
    public String handlerForm(@RequestParam("id") Integer id,
                              @RequestParam("email") String email,
                              @RequestParam("password") String password,
                              @RequestParam("password-new") String passwordNew){
       if(!password.equals(passwordNew)){
           AppUser appUser = userService.findByEmail(email);
           appUser.setPassword(passwordEncoder.encode(password));
           userService.save(appUser);
       }
        return "redirect:/admin/user-management/" + id;
    }

}
