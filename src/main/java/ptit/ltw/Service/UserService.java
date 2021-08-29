package ptit.ltw.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ptit.ltw.Entity.AppUser;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService extends UserDetailsService {
    void save(@NotNull AppUser appUser);
    void setAuthentication(HttpSession session, AppUser appUser);
    AppUser findByEmail(String email);
    AppUser findById(Long id);
    List<AppUser> getAll();
    void delete(Long id);
    void forgotPassword(String email);
    void updatePassword(String email, String password);
}
