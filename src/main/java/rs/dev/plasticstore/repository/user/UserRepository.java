package rs.dev.plasticstore.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);
}
