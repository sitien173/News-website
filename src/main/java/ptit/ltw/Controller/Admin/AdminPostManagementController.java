package ptit.ltw.Controller.Admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.CategoryService;
import ptit.ltw.Service.IService.FileStoreService;
import ptit.ltw.Service.IService.PostService;
import ptit.ltw.Service.IService.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/post-management")
@AllArgsConstructor
public class AdminPostManagementController {
    private final PostService postService;
    private final FileStoreService fileStoreService;
    private final CategoryService categoryService;
    private static List<Post> posts;
    private static List<Category> categories;

    private Post getPost(){
        return new Post();
    }

    private List<Post> getPosts(){
        posts = postService.getAll();
        return posts;
    }

    private List<Category> getCategories(){
        categories = categoryService.getAllIsEnable();
        return categories;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories(@RequestParam(value = "refresh",required = false) Boolean isRefresh){
        if(isRefresh != null) categories = null;
        return categories == null ? getCategories() : categories;
    }

    @ModelAttribute("posts")
    public List<Post> getPosts(@RequestParam(value = "refresh",required = false) Boolean isRefresh){
        if(isRefresh != null) posts = null;
        return posts == null ? getPosts() : posts;
    }

    @GetMapping
    public String showView(){
        return "admin/post-management";
    }

    @GetMapping("/add")
    public String showViewAdd(Model model){
        model.addAttribute("post", getPost());
        return "admin/post-management";
    }

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registrationForm(@Valid @ModelAttribute("post") Post post,
                                   BindingResult result,
                                   @RequestParam(value = "cateIds") int[] cateIds,
                                   @RequestParam("file") MultipartFile file,
                                   @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext,
                                   Model model) throws IOException {
        if(result.hasErrors()) {
            model.addAttribute("error",true);
            return "admin/post-management";
        }
        // upload file to uploads
        if(!file.isEmpty()) post.setBanner(fileStoreService.upload(file));

        updatePostCategory(post, cateIds);

        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        post.setAppUser(appUser);
        postService.save(post);
        return "redirect:/admin/post-management?refresh=true";
    }

    private void updatePostCategory(Post post,int[] cateIds) {
        for(int cateId : cateIds){
            categories.forEach(category -> {
                if(category.getId() == cateId){
                    post.addCategory(category);
                }
            });
        }
    }


    @GetMapping("/{id}")
    public String getInfo(@PathVariable("id") long id,Model model){
        model.addAttribute("postEdit",postService.findById(id));
        return "admin/post-edit";
    }

    @PostMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateForm(@Valid @ModelAttribute("postEdit") Post post,
                             BindingResult result,
                             @RequestParam("cateIds") int[] cateIds,
                             @RequestParam("file")MultipartFile file,
                             @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext) throws IOException {
        if(result.hasErrors()) return "admin/post-edit";
        else if( !file.isEmpty() )
            post.setBanner(fileStoreService.upload(file));

        updatePostCategory(post,cateIds);

        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        post.setAppUser(appUser);
        postService.save(post);
        return "redirect:/admin/post-management?refresh=true";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("postCheckId") long[] ids){
        for (long id : ids)  postService.delete(id);
        return "redirect:/admin/post-management?refresh=true";
    }
}
