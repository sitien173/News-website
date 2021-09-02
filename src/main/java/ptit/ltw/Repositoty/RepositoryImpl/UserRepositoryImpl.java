package ptit.ltw.Repositoty.RepositoryImpl;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Repositoty.UserRepository;

import java.util.Optional;

@Log4j2
@Repository
public class UserRepositoryImpl extends CrudCustomRepositoryImpl<AppUser,Long> implements UserRepository {

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return findByNaturalId(AppUser.class,"email",email);
    }

}
