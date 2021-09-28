package ptit.ltw.Repositoty.RepositoryImpl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.AppUser;
import ptit.ltw.Repositoty.IRepository.UserRepository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends CrudCustomRepositoryImpl<AppUser,Long> implements UserRepository{

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return findByNaturalId(AppUser.class,"email",email);
    }
}
