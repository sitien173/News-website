package ptit.ltw.Repositoty.IRepository;

import ptit.ltw.Entity.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudCustomRepository<Category,Integer> {
    Optional<Category> findBySlug(String slug);
    Optional<Category> findByName(String name);
}
