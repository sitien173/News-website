package ptit.ltw.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ptit.ltw.Entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService extends UserDetailsService {
    void save(@NotNull User user);
    User findByEmail(String email);
    User findByPhone(String phone);
    User findById(Long id);
    List<User> getAll();
    void delete(Long id);
    void forgotPassword(String email);
    void updatePassword(String email, String password);
}
