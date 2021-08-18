package ptit.ltw.Service.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ptit.ltw.Repositoty.UserDAO;
import ptit.ltw.Service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException(String.format("%s not found",username)));
    }
}
