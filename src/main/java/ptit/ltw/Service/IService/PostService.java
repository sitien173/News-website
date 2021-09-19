package ptit.ltw.Service.IService;

import ptit.ltw.Entity.Post;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface PostService {
    List<Post> getAll();
    List<Post> getAllIsEnable();
    Post findById(Long id);
    void delete(Long id);
    void save(@NotNull Post post);
}
