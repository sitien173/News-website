package ptit.ltw.Service.IService;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ptit.ltw.Entity.AppUser;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends UserDetailsService {
    void save(AppUser appUser);
    void setAuthentication(AppUser appUser);
    AppUser findByEmail(String email);
    AppUser findById(Long id);
    List<AppUser> getAll();
    void delete(Long id);
    void forgotPassword(String email);
}
