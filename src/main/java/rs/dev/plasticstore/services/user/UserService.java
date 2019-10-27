package rs.dev.plasticstore.services.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.dev.plasticstore.model.User;

public interface UserService extends UserDetailsService {


    void save(User user);
}
