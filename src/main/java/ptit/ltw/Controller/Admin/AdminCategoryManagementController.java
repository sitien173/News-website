package ptit.ltw.Controller.Admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Entity.Category;
import ptit.ltw.Service.IService.CategoryService;
import ptit.ltw.Service.IService.FileStoreService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/category-management")
@AllArgsConstructor
public class AdminCategoryManagementController {
    private final CategoryService categoryService;
    private final FileStoreService fileStoreService;
    private static List<Category> categories;


    private Category getCategory(){
        return new Category();
    }

    private List<Category> getCategories(){
        categories = categoryService.getAll();
        return categories;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories(@RequestParam(value = "refresh",required = false) Boolean isRefresh){
        if(isRefresh != null) categories = null;
        return categories == null ? getCategories() : categories;
    }

    @GetMapping
    public String showView(){
        return "admin/category-management";
    }

    @GetMapping("/add")
    public String showViewAdd(Model model){
        model.addAttribute("category", getCategory());
        return "admin/category-management";
    }

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registrationForm(@Valid @ModelAttribute("category") Category category,
                                   BindingResult result,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        if(result.hasErrors()) return "admin/category-management";
        else if(categoryService.findByName(category.getName()) != null){
            result.rejectValue("name","error","name is exist");
            return "admin/category-management";
        }
        // upload file to uploads
        if(!file.isEmpty()) category.setBanner(fileStoreService.upload(file));

        // TODO: check email isExist
        categoryService.save(category);
        return "redirect:/admin/category-management?refresh=true";
    }


    @GetMapping("/{id}")
    public String getInfo(@PathVariable("id") int id,Model model){
        model.addAttribute("categoryEdit",categoryService.findById(id));
        return "admin/category-edit";
    }

    @PostMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateForm(@Valid @ModelAttribute("categoryEdit") Category category,
                             BindingResult result,
                             @RequestParam("name1") String name,
                             @RequestParam("file")MultipartFile file) throws IOException {
        if(result.hasErrors()) return "admin/category-edit";
        else if (!category.getName().equals(name)){
            if(categoryService.findByName(name) != null){
                result.rejectValue("name","error","Name is exist");
                return "admin/category-edit";
            }
            category.setName(name);
        }
        else if( !file.isEmpty() )
            category.setBanner(fileStoreService.upload(file));
        // upload file to uploads
        // TODO: check email isExist
        categoryService.save(category);
        return "redirect:/admin/category-management?refresh=true";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("cateId") int[] ids){
        for (int id : ids)  categoryService.delete(id);
        return "redirect:/admin/category-management?refresh=true";
    }
}
