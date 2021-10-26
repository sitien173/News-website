package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Entity.Post;
import ptit.ltw.Repositoty.IRepository.PostRepository;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class PostRepositoryImpl extends CrudCustomRepositoryImpl<Post,Long> implements PostRepository {
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory,Post.class);
    }

    @Transactional
    @Override
    public Page<Post> getAll(Pageable pageable) {
        List<Post> list = sessionFactory.getCurrentSession()
                .createQuery("from Post where isEnable = true",domainClass)
                .setFirstResult(pageable.getPageSize()*(pageable.getPageNumber() - 1))
                .setMaxResults(pageable.getPageSize()).list();
        return new PageImpl<>(list,pageable,list.size());
    }

    @Override
    public Page<Post> getAll(String txt,Pageable pageable) {
        List<Post> list = sessionFactory.getCurrentSession()
                .createQuery("from Post where title like :txt and isEnable = true",domainClass)
                .setParameter("txt","%"+txt+"%")
                .setFirstResult(pageable.getPageSize()*(pageable.getPageNumber() - 1))
                .setMaxResults(pageable.getPageSize()).list();
        return new PageImpl<>(list,pageable,list.size());
    }

}
