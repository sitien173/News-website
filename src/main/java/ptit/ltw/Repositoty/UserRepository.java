package ptit.ltw.Repositoty;

import ptit.ltw.Entity.AppUser;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    void save(@NotNull AppUser appUser);
    void delete(Long id);
    Collection<AppUser> getAll();
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByPhone(String phone);
    Optional<AppUser> findById(Long id);
}
