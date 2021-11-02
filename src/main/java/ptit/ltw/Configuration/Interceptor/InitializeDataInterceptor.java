package ptit.ltw.Configuration.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ptit.ltw.Entity.Category;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.CategoryService;
import ptit.ltw.Service.IService.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InitializeDataInterceptor implements HandlerInterceptor {
    private final PostService postService;
    private final CategoryService categoryService;
    private final Environment env;
    private static Page<Post> posts;
    private static List<Post> postsPopular;
    private static List<Post> recommendPosts;
    private static List<Category> categories;
    private static List<Post> postsTayNguyen;
    private final static Integer pageSize = 10;
    private final static Integer POST_RECOMMEND_SIZE = 4;
    private final static String REQUEST_CATEGORY_CONTROLLER = "/category";
    private final static String REQUEST_POST_CONTROLLER = "/post";
    private final static String REQUEST_SEARCH_CONTROLLER = "/search";
    private final static String TAY_NGUYEN = "tay-nguyen";

    public InitializeDataInterceptor(PostService postService, CategoryService categoryService, Environment env) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.env = env;
    }

    private List<Category> getCategories(){
        if(categories == null) categories = categoryService.getAll();
        return categories;
    }
    private Page<Post> getPosts(Integer pageNo){
        posts = postService.getAll(pageNo, InitializeDataInterceptor.pageSize);
        return posts;
    }
    private List<Post> getNewPost(){
        return posts.getContent().stream().sorted(Comparator.comparing(Post::getId).reversed()).collect(Collectors.toList());
    }
    private List<Post> getPopularPost(){
        if(postsPopular == null) {
            postsPopular = posts.stream()
                    .sorted(Comparator.comparing(Post::getView).thenComparing(Post::getCreateAt).reversed())
                    .collect(Collectors.toList());
        }
        return postsPopular;
    }

    private List<Post> getRecommendPost(){
        if(recommendPosts == null) {
            recommendPosts = posts.getContent();
            int size = Math.min(recommendPosts.size(), POST_RECOMMEND_SIZE);
            recommendPosts = recommendPosts.stream().sorted((o1, o2) -> o1.getComments().size() & o2.getComments().size()).collect(Collectors.toList()).subList(0,size);
        }
        return recommendPosts;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        int pageNo;
        if(request.getParameter("pageNo") != null)
            pageNo = Integer.parseInt(request.getParameter("pageNo"));
        else pageNo = 1;

        String referer =  request.getHeader(HttpHeaders.REFERER) != null ? request.getHeader(HttpHeaders.REFERER) : "";
        List<Post> listPost = getPosts(pageNo).getContent().stream().sorted(Comparator.comparing(Post::getId).reversed()).collect(Collectors.toList());

        if(referer.contains(REQUEST_POST_CONTROLLER) ||
           referer.contains(REQUEST_CATEGORY_CONTROLLER) ||
           referer.contains(REQUEST_SEARCH_CONTROLLER)){
            modelAndView.addObject("posts",listPost);
            modelAndView.addObject("postsPopular",getPopularPost());
            modelAndView.addObject("categories",getCategories());
            modelAndView.addObject("recommendPosts",getRecommendPost());
            modelAndView.addObject("newPosts",getNewPost());
            modelAndView.addObject("link",env.getRequiredProperty("base.url"));
        }
        else {
            // filter post thuoc tay nguyen
            if(postsTayNguyen == null) postsTayNguyen = new ArrayList<>();
            else postsTayNguyen.clear();
            listPost.forEach(p -> p.getCategories().forEach(c -> {if(c.getSlug().equalsIgnoreCase(TAY_NGUYEN)) postsTayNguyen.add(p);}));

            modelAndView.addObject("posts",listPost);
            modelAndView.addObject("postsPopular",getPopularPost());
            modelAndView.addObject("postsTayNguyen",postsTayNguyen);
            modelAndView.addObject("newPosts",getNewPost());
            modelAndView.addObject("recommendPosts",getRecommendPost());
            modelAndView.addObject("categories",getCategories());
            modelAndView.addObject("currentPage",pageNo);
            modelAndView.addObject("totalPage",posts.getTotalPages());
            modelAndView.addObject("link",env.getRequiredProperty("base.url"));
        }
    }

}
