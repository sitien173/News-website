package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CategoryRepository;
import ptit.ltw.Service.IService.CategoryService;
import ptit.ltw.Utils.VNCharacterUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        return new ArrayList<>(categoryRepository.getAll());
    }

    @Override
    public List<Category> getAllIsEnable() {
       return this.getAll()
               .stream().filter(Category::getIsEnable).collect(Collectors.toList());
    }

    @Override
    public Category findBySlug(String slug) {
        return categoryRepository.findBySlug(slug).orElse(null);
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.delete(id);
    }


    @Override
    public void save(@NotNull Category category) {
        // Thêm mới chưa có slug -> tự độgn chuyển title xuống slug
        if(category.getSlug() == null || category.getSlug().isEmpty()){
            category.setSlug(VNCharacterUtils.removeAccent(category.getName()));
        }
        categoryRepository.save(category);
    }
}
