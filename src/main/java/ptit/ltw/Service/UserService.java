package ptit.ltw.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import ptit.ltw.Entity.User;

import javax.validation.constraints.NotNull;

public interface UserService extends UserDetailsService {
    void save(@NotNull User user);
    User findByEmail(String email);
    User findByPhone(String phone);
    void forgotPassword(String email);
    void updatePassword(String email, String password);
}
