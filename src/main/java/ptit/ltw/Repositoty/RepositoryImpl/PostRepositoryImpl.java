package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PostRepositoryImpl extends CrudCustomRepositoryImpl<Post,Long> implements PostRepository {
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
