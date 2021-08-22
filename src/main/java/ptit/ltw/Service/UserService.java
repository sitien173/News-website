package ptit.ltw.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ptit.ltw.Dto.UserDto;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService extends UserDetailsService {
    void save(@NotNull UserDto user,String password);
    void setAuthentication(HttpSession session, Long userId);
    UserDto findByEmail(String email);
    UserDto findByPhone(String phone);
    UserDto findById(Long id);
    List<UserDto> getAll();
    void delete(Long id);
    void forgotPassword(String email);
    void updatePassword(String email, String password);
}
