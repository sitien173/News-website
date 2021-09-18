package ptit.ltw.Repositoty.RepositoryImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.Category;
import ptit.ltw.Repositoty.IRepository.CategoryRepository;

import java.util.Optional;

@Repository
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
        Optional<Category> optional = Optional.empty();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            String HQL = "from Category where name = :name";
            optional = session.createQuery(HQL,Category.class).setParameter("name",name)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
        return optional;
    }
}

