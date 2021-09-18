package ptit.ltw.Repositoty.RepositoryImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Entity.Category;
import ptit.ltw.Entity.Post;
import ptit.ltw.Repositoty.IRepository.PostRepository;

@Repository
public class PostRepositoryImpl extends CrudCustomRepositoryImpl<Post,Long> implements PostRepository {
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void delete(Long id) {
        Session session =  null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            Post post = session.getReference(Post.class,id);
            // remove all posts
            Category category = post.getCategory();
            AppUser appUser = post.getAppUser();

            category.getPosts().removeIf(p -> post.getId().equals(p.getId()));
            session.saveOrUpdate(category);

            appUser.getPosts().removeIf(p -> post.getId().equals(p.getId()));
            session.saveOrUpdate(appUser);

            session.delete(post);
            tr.commit();
        } catch (HibernateException e) {
            if(tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
    }
}
