package ptit.ltw.Configuration.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ptit.ltw.Entity.Category;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.CategoryService;
import ptit.ltw.Service.IService.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
public class GetCategoriesAndPostInterceptor implements HandlerInterceptor {
    private final PostService postService;
    private final CategoryService categoryService;

    public GetCategoriesAndPostInterceptor(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    private List<Category> getCategories(){
        return categoryService.getAllIsEnable();
    }
    private List<Post> getPosts(){
        return postService.getAllIsEnable();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("categories",getCategories());
        modelAndView.addObject("post",getPosts());
    }

}
