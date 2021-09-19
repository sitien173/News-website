package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CategoryRepository;

import java.util.Optional;

@Repository
@Slf4j
public class CategoryRepositoryImpl extends CrudCustomRepositoryImpl<Category,Integer> implements CategoryRepository {
    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return findByNaturalId(Category.class, "slug", slug);
    }

    @Override
    public Optional<Category> findByName(String name) {
        Session session =  null;
        Transaction tr = null;
        Optional<Category> optional = Optional.empty();
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            String HQL = "from Category where name = :name";
            optional = session.createQuery(HQL,Category.class).setParameter("name",name)
                    .uniqueResultOptional();
            tr.commit();
        } catch (HibernateException e) {
            log.error("Closing session after rollback error: ", e);
            if(tr != null && tr.isActive()) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()) session.close();
        }
        return optional;
    }
}

