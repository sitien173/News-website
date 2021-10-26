package ptit.ltw.Controller.User;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ptit.ltw.Entity.Post;
import ptit.ltw.Service.IService.PostService;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchPostController {
    private final PostService postService;
    private final static Integer pageSize = 10;
    private static Integer totalPage;

    @SneakyThrows
    @GetMapping
    public String handlerSearch(@RequestParam("query") String query,
                                @RequestParam(value = "pageNo",required = false) Integer paramPageNo,
                                Model model){
       String result = java.net.URLDecoder.decode(query, StandardCharsets.UTF_8.name());
       int pageNo = paramPageNo == null ? 1 : paramPageNo;
       Page<Post> page = postService.getAll(result,pageNo,pageSize);
       totalPage = totalPage == null ? page.getTotalPages() : totalPage;
       model.addAttribute("currentPage",pageNo);
       model.addAttribute("totalPage",totalPage);
       model.addAttribute("query",result);
       model.addAttribute("resultPost",page.getContent().stream()
               .sorted(Comparator.comparing(Post::getId).reversed()).collect(Collectors.toList()));
       return "user/result-query";
    }
}
