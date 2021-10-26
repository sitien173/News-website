package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.Comment;
import ptit.ltw.Repositoty.IRepository.CommentRepository;

import java.util.List;

@Repository
public class CommentRepositoryImpl extends CrudCustomRepositoryImpl<Comment,Long> implements CommentRepository  {
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Comment.class);
    }

    @SneakyThrows
    @Transactional
    @Override
    public Page<Comment> getAll(Long postId, Pageable pageable) {
        String HQL = "From Comment where post.id = "+postId+"";
        List<Comment> list = sessionFactory.getCurrentSession().createQuery(HQL,domainClass).setFirstResult(pageable.getPageSize()*(pageable.getPageNumber() - 1))
                .setMaxResults(pageable.getPageSize()).list();
        return new PageImpl<>(list);
    }
}
