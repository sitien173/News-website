package ptit.ltw.Repositoty;

import ptit.ltw.Entity.AppUser;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends CrudCustomRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
}
