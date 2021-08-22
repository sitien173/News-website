package ptit.ltw.Repositoty;

import ptit.ltw.Entity.User;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(@NotNull User user);
    void update(@NotNull User user);
    void delete(Long id);
    Collection<? extends User> getAll();
    Optional<? extends User> findByEmail(String email);
    Optional<? extends User> findByPhone(String phone);
    Optional<? extends User> findById(Long id);
}
