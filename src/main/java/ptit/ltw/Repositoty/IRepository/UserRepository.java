package ptit.ltw.Repositoty.IRepository;

import ptit.ltw.Entity.AppUser;

import java.util.Optional;

public interface UserRepository extends CrudCustomRepository<AppUser,Long>{
    Optional<AppUser> findByEmail(String email);
}
