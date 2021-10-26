package ptit.ltw.Service.IService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ptit.ltw.Entity.Post;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface PostService {
    List<Post> getAll();
    Page<Post> getAll(Integer pageNo, Integer pageSize);
    Page<Post> getAll(String txtSearch,Integer pageNo, Integer pageSize);
    Post findBySlug(String slug);
    Post findById(Long id);
    void delete(Long id);
    void save(@NotNull Post post);
}
