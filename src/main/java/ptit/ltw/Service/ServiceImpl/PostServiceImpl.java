package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ptit.ltw.Entity.Post;
import ptit.ltw.Repositoty.IRepository.PostRepository;
import ptit.ltw.Service.IService.PostService;
import ptit.ltw.Service.IService.SubscriberService;
import ptit.ltw.Utils.VNCharacterUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubscriberService subscriberService;
    @Override
    public List<Post> getAll() {
        return new ArrayList<>(postRepository.getAll());
    }

    @Override
    public Page<Post> getAll(String txtSearch, Integer pageNo, Integer pageSize) {
        return postRepository.getAll(txtSearch,PageRequest.of(pageNo,pageSize));
    }

    @Override
    public Page<Post> getAll(Integer pageNo, Integer pageSize) {
        return postRepository.getAll(PageRequest.of(pageNo,pageSize));
    }

    @Override
    public Post findBySlug(String slug) {
        return postRepository.findByNaturalId(slug).orElse(null);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        postRepository.delete(id);
    }

    private String getSlug(String title){
        String slug = VNCharacterUtils.removeAccent(title);
        if(findBySlug(slug) != null)  slug += LocalDateTime.now();
        return slug;
    }

    @Override
    public void save(@NotNull Post post) {
        // Thêm mới chưa có slug -> tự độgn chuyển title xuống slug
        if(post.getSlug() == null || post.getSlug().isEmpty()){
            post.setSlug(getSlug(post.getTitle()));
        }else if(!VNCharacterUtils.removeAccent(post.getTitle()).equals(post.getSlug())){
           post.setSlug(getSlug(post.getTitle()));
        }
        postRepository.save(post);
        if(post.getCreateAt().isEqual(LocalDate.now()) && post.getView() == 0){
            subscriberService.sendNewPostToSub(post);
        }
    }
}
