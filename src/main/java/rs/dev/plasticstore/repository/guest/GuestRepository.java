package rs.dev.plasticstore.repository.guest;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Guest;

import java.util.ArrayList;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    ArrayList<Guest> findAll();

    Optional<Guest> findByUsername(String username);

    Guest findGuestById(int id);

    Guest findGuestByUsername(String username);

}
