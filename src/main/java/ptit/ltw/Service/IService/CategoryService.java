package ptit.ltw.Service.IService;

import ptit.ltw.Entity.Category;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category findBySlug(String slug);
    Category findById(Integer id);
    Category findByName(String name);
    void delete(Integer id);
    void save(@NotNull Category category);
}
