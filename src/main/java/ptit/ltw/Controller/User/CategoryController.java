package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Entity.Category;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.CategoryService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final static Integer PAGE_SIZE = 10;

    @GetMapping("/{slug}")
    public String viewCategory(@PathVariable("slug") String slug,
                               @RequestParam(value = "pageNo",required = false) Integer pageNo,
                               @RequestParam(value = "view-all",required = false) boolean isViewAll,
                               Model model){
        Category category = categoryService.findBySlug(slug);
        if(category == null) return "error/404";
        List<Post> posts = category.getPosts()
                .stream().sorted(Comparator.comparing(Post::getId).reversed())
                .collect(Collectors.toList());

        int pageNo1 = pageNo != null ? pageNo : 1;
        int totalEl = posts.size();
        int pageSize = Math.min(totalEl, pageNo1 * PAGE_SIZE);
        if(isViewAll){
            pageNo1 = 1;
            pageSize = totalEl;
        }
        model.addAttribute("cPosts",posts.subList(PAGE_SIZE*(pageNo1 - 1),pageSize));
        model.addAttribute("currentPage",pageNo1);
        model.addAttribute("totalPage",totalEl/pageSize);
        model.addAttribute("category",category);
        return "user/category";
    }
}
