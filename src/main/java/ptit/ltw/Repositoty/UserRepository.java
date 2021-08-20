package ptit.ltw.Repositoty;

import ptit.ltw.Entity.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserRepository {
    void save(@NotNull User user);
    void update(@NotNull User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
