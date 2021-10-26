package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Comment;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.CommentService;
import ptit.ltw.Service.IService.PostService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/feedback")
@AllArgsConstructor
public class FeedbackController {
    private final PostService postService;
    private final CommentService commentService;
    private final Environment env;
    @PostMapping("/add")
    public String handlerFeedback(@RequestParam("postId") Long postId,
                                  @RequestParam("feedback") String feedback,
                                  HttpServletRequest request,
                                  @SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext securityContext){
        Post post = postService.findById(postId);
        String referer =  request.getHeader(HttpHeaders.REFERER).replaceAll(env.getRequiredProperty("base.url"),"");
        if(post == null) return "redirect:" + referer + "?message=Gui phan hoi that bai";
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setFeedback(feedback);
        comment.setAppUser((AppUser) securityContext.getAuthentication().getPrincipal());
        commentService.save(comment);
        return "redirect:" + referer + "?message=Gui phan hoi thanh cong";
    }
}
