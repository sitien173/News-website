package ptit.ltw.Service.IService;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.AppUser;

import javax.servlet.http.HttpSession;
import java.util.List;

@Transactional
public interface UserService extends UserDetailsService {
    void save(AppUser appUser);
    void update(AppUser appUser);
    void setAuthentication(AppUser appUser);
    AppUser findByEmail(String email);
    AppUser findById(Long id);
    List<AppUser> getAll();
    void delete(Long id);
    void forgotPassword(String email);
    void updatePassword(String email, String password);
}
