package ptit.ltw.Repositoty;

import ptit.ltw.Entity.AppUser;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    void save(@NotNull AppUser appUser);
    void update(@NotNull AppUser appUser);
    void delete(Long id);
    Collection<? extends AppUser> getAll();
    Optional<? extends AppUser> findByEmail(String email);
    Optional<? extends AppUser> findByPhone(String phone);
    Optional<? extends AppUser> findById(Long id);
}
