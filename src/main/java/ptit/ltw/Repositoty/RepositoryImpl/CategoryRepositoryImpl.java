package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CategoryRepository;

import java.util.Optional;

@Repository
@Slf4j
public class CategoryRepositoryImpl extends CrudCustomRepositoryImpl<Category,Integer> implements CategoryRepository {
    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory,Category.class);
    }
    @Override
    public Optional<Category> findBySlug(String slug) {
        return findByNaturalId(slug);
    }

    @Transactional
    @Override
    public Optional<Category> findByName(String name) {
        String HQL = "from Category where name = :name and isEnable = true";
        return sessionFactory.getCurrentSession().createQuery(HQL,Category.class).setParameter("name",name)
                .uniqueResultOptional();
    }
}

