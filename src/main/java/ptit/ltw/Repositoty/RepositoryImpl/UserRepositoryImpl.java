package ptit.ltw.Repositoty.RepositoryImpl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Repositoty.IRepository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends CrudCustomRepositoryImpl<AppUser,Long> implements UserRepository{

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory,AppUser.class);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return findByNaturalId(email);
    }
}
