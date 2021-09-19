package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.Post;
import ptit.ltw.Repositoty.IRepository.PostRepository;
import ptit.ltw.Service.IService.PostService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Override
    public List<Post> getAll() {
        return new ArrayList<>(postRepository.getAll(Post.class));
    }

    @Override
    public List<Post> getAllIsEnable() {
     return this.getAll()
             .stream()
             .filter(Post::getIsEnable).collect(Collectors.toList());
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(Post.class,id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        postRepository.delete(id);
    }

    @Override
    public void save(@NotNull Post post) {
        postRepository.save(post);
    }
}
