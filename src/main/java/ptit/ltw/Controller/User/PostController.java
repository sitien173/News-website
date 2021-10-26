package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.ltw.Entity.Comment;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.CommentService;
import ptit.ltw.Service.IService.PostService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final static Integer pageSize = 10;
    private final static List<Comment> comments = new ArrayList<>();
    @GetMapping("/{slug}")
    public String viewPost(@PathVariable("slug") String slug,
                           @RequestParam(value = "pageNo",required = false) Integer pageNo,
                           Model model){
        Post post = postService.findBySlug(slug);
        if(post == null) return "error/404";
        else {
            // TODO: update view post + 1
            post.setView(post.getView() + 1);
            postService.save(post);
            model.addAttribute("post",post);
            // Get comment post
            int pageNo1 = pageNo != null ? pageNo : 1;
            Page<Comment> page = commentService.getAll(post.getId(), pageNo1, pageSize);
            post.setComments(page != null ? page.getContent() : comments);
            model.addAttribute("currentPage",pageNo1);
            model.addAttribute("totalPage",page != null ? page.getTotalPages() : 0);
        }
        return "user/single-post";
    }
}
