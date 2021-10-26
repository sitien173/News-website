package ptit.ltw.Repositoty.IRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ptit.ltw.Entity.Post;

import java.util.Collection;

public interface PostRepository extends CrudCustomRepository<Post,Long> {
    Page<Post> getAll(Pageable pageable);
    Page<Post> getAll(String txt,Pageable pageable);
}
