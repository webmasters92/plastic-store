package rs.dev.plasticstore.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.dev.plasticstore.model.User;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.repository.user.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(()-> new UsernameNotFoundException("Not found: "+username));
        return user.map(UserPrincipal::new).get();
    }
}
