package rs.dev.plasticstore.repository.guest;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Guest;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    Optional<Guest> findByUsername(String username);

    Guest findGuestByUsername(String username);

}
