package ptit.ltw.Service.IService;

import org.springframework.data.domain.Page;
import ptit.ltw.Entity.Comment;

public interface CommentService {
    Page<Comment> getAll(Long postId, Integer pageNo,Integer pageSize);
    void save(Comment comment);
}
