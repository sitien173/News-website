package ptit.ltw.Repositoty.IRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ptit.ltw.Entity.Comment;

public interface CommentRepository extends CrudCustomRepository<Comment,Long> {
    Page<Comment> getAll(Long postId, Pageable pageable);
}
