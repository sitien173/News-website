package ptit.ltw.Repositoty.IRepository;

import ptit.ltw.Entity.Post;

public interface PostRepository extends CrudCustomRepository<Post,Long> {
    void delete(Long aLong);
}
