package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.Comment;
import ptit.ltw.Repositoty.IRepository.CommentRepository;
import ptit.ltw.Service.IService.CommentService;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Page<Comment> getAll(Long postId, Integer pageNo,Integer pageSize) {
        return commentRepository.getAll(postId, PageRequest.of(pageNo,pageSize));
    }

    @Override
    public void save(@NotNull Comment comment) {
        commentRepository.save(comment);
    }
}
