package ptit.ltw.Repositoty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ptit.ltw.Entity.User;

import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
